package mikhailsnnt.taxresident.common

import mikhailsnnt.taxresident.common.model.*
import mikhailsnnt.taxresident.common.model.enums.*
import mikhailsnnt.taxresident.common.model.wrappers.*

data class TxContext(
    // General request info
    var requestId: TxRequestId = TxRequestId.NONE,
    var command: TxCommand = TxCommand.NONE,
    var state: TxState = TxState.NONE,

    // Request data
    var periodRequest: TxPeriod = TxPeriod(),

    // Response
    var singlePeriodResponse: TxPeriod = TxPeriod(),
    var multiplePeriodsResponse: MutableList<TxPeriod> = mutableListOf(),
    var taxResidencyResponse: TxResidencyInfo = TxResidencyInfo(),

    var errors: MutableList<TxError> = mutableListOf(),

    //Debug info
    var workMode: TxWorkMode = TxWorkMode.PROD,
    var stubCase: TxStubCase = TxStubCase.NONE
)