package mikhailsnnt.taxresident.common

import mikhailsnnt.taxresident.common.model.TxError
import mikhailsnnt.taxresident.common.model.TxPeriod
import mikhailsnnt.taxresident.common.model.TxResidencyInfo
import mikhailsnnt.taxresident.common.model.enums.TxCommand
import mikhailsnnt.taxresident.common.model.enums.TxState
import mikhailsnnt.taxresident.common.model.enums.TxStubCase
import mikhailsnnt.taxresident.common.model.enums.TxWorkMode
import mikhailsnnt.taxresident.common.model.wrappers.TxUserId

data class TxContext(
    // General request info
    var requestId: String = "",
    var command: TxCommand = TxCommand.NONE,
    var userId: TxUserId = "",
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