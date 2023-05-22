package mikhailsnnt.taxresident.common.mappers

import mikhail.snnt.taxresident.api.v1.models.*
import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.mappers.exceptions.UnknownRequestException
import mikhailsnnt.taxresident.common.model.enums.TxCommand


fun TxContext.fromTransport(request: IRequest) = apply {
    when (request) {
        is TaxResidencyRequest -> fromTransport(request)
        is PeriodCreateRequest -> fromTransport(request)
        is PeriodDeleteRequest -> fromTransport(request)
        is PeriodReadRequest -> fromTransport(request)
        is PeriodUpdateRequest -> fromTransport(request)
        else -> throw UnknownRequestException(request::class)
    }
}

fun TxContext.fromTransport(request: TaxResidencyRequest) = apply {
    requestId = request.requestId ?: ""
    command = TxCommand.CALCULATE_RESIDENCY
}

fun TxContext.fromTransport(request: PeriodCreateRequest) = apply {
    requestId = request.requestId()
    command = TxCommand.CREATE

    periodRequest = request.period.fromTransportPeriod()

    workMode = request.debug.transportWorkMode()
    stubCase = request.debug.transportStubCase()
}

fun TxContext.fromTransport(request: PeriodReadRequest) = apply {
    requestId = request.requestId()
    command = TxCommand.READ


    workMode = request.debug.transportWorkMode()
    stubCase = request.debug.transportStubCase()
}

fun TxContext.fromTransport(request: PeriodUpdateRequest) = apply {
    requestId = request.requestId()
    command = TxCommand.UPDATE
    periodRequest = request.period.fromTransportPeriod()

    workMode = request.debug.transportWorkMode()
    stubCase = request.debug.transportStubCase()
}

fun TxContext.fromTransport(request: PeriodDeleteRequest) = apply {
    requestId = request.requestId()
    command = TxCommand.DELETE
    periodRequest = request.deleteObject.fromTransportPeriod()

    workMode = request.debug.transportWorkMode()
    stubCase = request.debug.transportStubCase()
}

