package mikhailsnnt.taxresiden.app.ktor

import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.collections.*
import mikhail.snnt.taxresident.api.multi.v2.apiV2Mapper
import mikhailsnnt.taxresiden.app.ktor.api.period
import mikhailsnnt.taxresiden.app.ktor.api.taxResidency
import mikhailsnnt.taxresiden.app.ktor.api.txHandler
import mikhailsnnt.taxresident.biz.TxProcessor
import mikhailsnnt.taxresident.common.TxProcessorSettings
import mikhailsnnt.taxresident.common.repo.period.IPeriodRepository
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


    val settings = TxProcessorSettings(
        periodRepositoryProd = IPeriodRepository.NONE,
        periodRepositoryTest = PeriodYdbRepository(
            environment.config.property("periodRepoBaseUrl").getString()
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