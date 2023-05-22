package mikhailsnnt.taxresiden.app.ktor

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import mikhail.snnt.taxresident.api.multi.v2.apiV2Mapper
import mikhailsnnt.taxresiden.app.ktor.api.period
import mikhailsnnt.taxresiden.app.ktor.api.taxResidency
import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.model.TxPeriod
import mikhailsnnt.taxresident.common.model.enums.TxCommand


fun main(){
    embeddedServer(CIO, applicationEngineEnvironment{
        connector{
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

    install(ContentNegotiation){
        json(apiV2Mapper)
    }

    //Pre stub
    val processor = object : TxProcessor{

        val periods = mutableSetOf<TxPeriod>()

        override fun process(txContext: TxContext){
            with(txContext){
                when(command) {
                    TxCommand.CREATE -> {
                        periods += periodRequest
                        singlePeriodResponse = periodRequest
                    }
                    TxCommand.READ ->
                        multiplePeriodsResponse = periods.toMutableList()
                    else -> throw RuntimeException("Command $command not supported by temporary stub")
                }
            }
        }
    }

    routing {
        period(processor)
        taxResidency(processor)
    }
}