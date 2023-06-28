package mikhailsnnt.taxresiden.app.ktor

import io.grpc.LoadBalancerRegistry
import io.grpc.internal.PickFirstLoadBalancerProvider
import io.ktor.client.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.collections.*
import kotlinx.serialization.json.Json
import mikhail.snnt.taxresident.api.multi.v2.apiV2Mapper
import mikhailsnnt.taxresiden.app.ktor.api.period
import mikhailsnnt.taxresiden.app.ktor.api.taxResidency
import mikhailsnnt.taxresiden.app.ktor.api.txHandler
import mikhailsnnt.taxresident.biz.TxProcessor
import mikhailsnnt.taxresident.common.TxProcessorSettings
import mikhailsnnt.taxresident.repo.ydb.PeriodYdbRepository
import java.time.Duration



fun main() {
    embeddedServer(CIO, applicationEngineEnvironment {
        connector {
            host = "0.0.0.0"
            port = 8080
        }
    }).apply {
        addShutdownHook {
            println("Shutting down")
        }

        start(true)
    }

}

val applicationHttpClient = HttpClient(io.ktor.client.engine.cio.CIO) {
    install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
        json(Json{ignoreUnknownKeys = true})
    }
}

fun Application.module() {

    install(Routing)
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
        contentConverter = KotlinxWebsocketSerializationConverter(apiV2Mapper)
    }

    install(ContentNegotiation) {
        json(apiV2Mapper)
    }

    LoadBalancerRegistry.getDefaultRegistry().register(PickFirstLoadBalancerProvider())

    val settings = TxProcessorSettings(
        periodRepositoryProd = PeriodYdbRepository(
            System.getenv("periodRepoProdBaseUrl"),
        ),
        periodRepositoryTest = PeriodYdbRepository(
            System.getenv("periodRepoTestBaseUrl")
        )
    )


    val processor = TxProcessor(settings)

    routing {
        webSocket("ws"){
            txHandler(processor, ConcurrentSet())
        }
        period(processor)
        taxResidency(processor)
    }
}