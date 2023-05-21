package mikhailsnnt.taxresident.common.mappers

import mikhail.snnt.taxresident.api.v1.models.*
import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.mappers.exceptions.UnknownCommandException
import mikhailsnnt.taxresident.common.model.enums.TxCommand

fun TxContext.toTransport(): IResponse =
    when (command) {
        TxCommand.CREATE -> toTransportCreate()
        TxCommand.READ -> toTransportRead()
        TxCommand.UPDATE -> toTransportUpdate()
        TxCommand.DELETE -> toTransportDelete()
        TxCommand.CALCULATE_RESIDENCY -> toTransportResidencyInfo()
        else -> throw UnknownCommandException(command)
    }


fun TxContext.toTransportCreate()= PeriodCreateResponse(
    requestId = requestId,
    responseType = command.toDiscriminator(),
    result = state.toTransport(),
    errors = errors.toTransport(),

    period = singlePeriodResponse.toTransport()
)


fun TxContext.toTransportRead()= PeriodReadResponse(
    requestId = requestId,
    responseType = command.toDiscriminator(),
    result = state.toTransport(),
    errors = errors.toTransport(),

    periods = multiplePeriodsResponse.toTransport()
)


fun TxContext.toTransportUpdate()= PeriodUpdateResponse(
    requestId = requestId,
    responseType = command.toDiscriminator(),
    result = state.toTransport(),
    errors = errors.toTransport(),

    period = singlePeriodResponse.toTransport()
)


fun TxContext.toTransportDelete()= PeriodDeleteResponse(
    requestId = requestId,
    responseType = command.toDiscriminator(),
    result = state.toTransport(),
    errors = errors.toTransport()
)


fun TxContext.toTransportResidencyInfo()= TaxResidencyResponse(
    requestId = requestId,
    responseType = command.toDiscriminator(),
    result = state.toTransport(),
    errors = errors.toTransport(),

    residencyInfo = taxResidencyResponse.toTransport()
)

