package mikhailsnnt.taxresident.common.mappers

import mikhail.snnt.taxresident.api.v1.models.*
import mikhailsnnt.taxresident.common.model.TxError
import mikhailsnnt.taxresident.common.model.TxPeriod
import mikhailsnnt.taxresident.common.model.TxResidencyInfo
import mikhailsnnt.taxresident.common.model.enums.TxCommand
import mikhailsnnt.taxresident.common.model.enums.TxState
import mikhailsnnt.taxresident.common.model.enums.TxStubCase
import mikhailsnnt.taxresident.common.model.enums.TxWorkMode
import mikhailsnnt.taxresident.common.model.wrappers.TxDate
import mikhailsnnt.taxresident.common.model.wrappers.TxPeriodId
import mikhailsnnt.taxresident.common.model.wrappers.TxRequestId


internal fun IRequest.requestId() = requestId?.let { TxRequestId(it) } ?: TxRequestId.NONE

internal fun String?.periodId() = this?.let { TxPeriodId(it) } ?: TxPeriodId.NONE

internal fun String?.date() = this?.let { TxDate(it) } ?: TxDate.NONE

internal fun PeriodDebug?.transportWorkMode() = when (this?.mode) {
    PeriodRequestDebugMode.PROD -> TxWorkMode.PROD
    PeriodRequestDebugMode.TEST -> TxWorkMode.TEST
    PeriodRequestDebugMode.STUB -> TxWorkMode.STUB
    null -> TxWorkMode.PROD
}

internal fun PeriodDebug?.transportStubCase() = when (this?.stub) {
    PeriodRequestDebugStubs.SUCCESS -> TxStubCase.SUCCESS
    PeriodRequestDebugStubs.NOT_FOUND -> TxStubCase.NOT_FOUND
    PeriodRequestDebugStubs.BAD_DATE_FORMAT -> TxStubCase.BAD_DATE_FORMAT
    null -> TxStubCase.NONE
}


internal fun PeriodDeleteObject?.fromTransportPeriod() = TxPeriod(this?.id.periodId())

internal fun PeriodUpdateObject?.fromTransportPeriod() = TxPeriod(
    this?.id.periodId(),
    startDate = this?.startDate.date(),
    endDate = this?.endDate.date()
)

internal fun PeriodCreateObject?.fromTransportPeriod() = TxPeriod(
    startDate = this?.startDate.date(),
    endDate = this?.endDate.date(),
)


internal fun TxCommand.toDiscriminator() = when (this) {
    TxCommand.CALCULATE_RESIDENCY -> "calculateResidency"
    TxCommand.CREATE -> "create"
    TxCommand.READ -> "read"
    TxCommand.UPDATE -> "update"
    TxCommand.DELETE -> "delete"

    TxCommand.NONE -> throw IllegalStateException("$this command can't be mapped to discriminator")
}

internal fun TxState.toTransport() =
    if (this == TxState.COMPLETED)
        ResponseResult.SUCCESS
    else
        ResponseResult.ERROR


internal fun List<TxError>.toTransport() =
    this.map {
        Error(
            code = it.code,
            group = it.group,
            field = it.field,
            message = it.message
        )
    }.toList()


internal fun TxPeriod.toTransport() =
    PeriodResponseObject(
        id = id.toString(),
        startDate = startDate.toString(),
        endDate = endDate.toString()
    )

internal fun Collection<TxPeriod>.toTransport() =
    this.map { it.toTransport() }.toList()


internal fun TxResidencyInfo.toTransport() = BaseTaxResidencyInfo(
    willLoseResidency,
    dateOfResidencyLoss.toString()
)
