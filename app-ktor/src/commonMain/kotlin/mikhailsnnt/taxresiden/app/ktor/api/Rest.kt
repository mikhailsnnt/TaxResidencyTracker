package mikhailsnnt.taxresiden.app.ktor.api

import io.ktor.server.application.*
import io.ktor.server.routing.*
import mikhailsnnt.taxresiden.app.ktor.*
import mikhailsnnt.taxresident.biz.TxProcessor


fun Route.period(processor: TxProcessor){
    route("period"){
        post("save"){
            call.savePeriod(processor)
        }

        post("read"){
            call.readPeriod(processor)
        }

        post("update"){
            call.updatePeriod(processor)
        }

        post("delete"){
            call.deletePeriod(processor)
        }
    }
}

fun Route.taxResidency(processor: TxProcessor){
    route("residency"){
        post("info"){
            call.residencyInfo(processor)
        }
    }
}