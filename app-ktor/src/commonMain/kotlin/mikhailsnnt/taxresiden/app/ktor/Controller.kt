package mikhailsnnt.taxresiden.app.ktor

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import mikhail.snnt.taxresident.api.v1.models.*
import mikhailsnnt.taxresident.biz.TxProcessor
import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.mappers.fromTransport
import mikhailsnnt.taxresident.common.mappers.toTransport



suspend fun ApplicationCall.savePeriod(processor: TxProcessor) = processCall<PeriodCreateRequest>(processor)

suspend fun ApplicationCall.readPeriod(processor: TxProcessor) = processCall<PeriodReadRequest>(processor)

suspend fun ApplicationCall.updatePeriod(processor: TxProcessor) = processCall<PeriodUpdateRequest>(processor)

suspend fun ApplicationCall.deletePeriod(processor: TxProcessor) = processCall<PeriodDeleteRequest>(processor)

suspend fun ApplicationCall.residencyInfo(processor: TxProcessor) = processCall<TaxResidencyRequest>(processor)

suspend inline fun <reified Q: IRequest> ApplicationCall.processCall(processor: TxProcessor){
    val context = TxContext()
    val request = receive<Q>()

    context.fromTransport(request)

    processor.exec(context)

    respond(context.toTransport())
}