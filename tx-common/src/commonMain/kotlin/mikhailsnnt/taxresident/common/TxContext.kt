package mikhailsnnt.taxresident.common

import mikhailsnnt.taxresident.common.model.TxPeriod
import mikhailsnnt.taxresident.common.model.TxResidencyInfo
import mikhailsnnt.taxresident.common.enums.TxCommand
import mikhailsnnt.taxresident.common.enums.TxState
import mikhailsnnt.taxresident.common.enums.TxStubCase
import mikhailsnnt.taxresident.common.enums.TxWorkMode
import mikhailsnnt.taxresident.common.model.wrappers.TxUserId
import mikhailsnnt.taxresident.common.repo.period.IPeriodRepository

data class TxContext(
    var settings: TxProcessorSettings = TxProcessorSettings.NONE,

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
    var stubCase: TxStubCase = TxStubCase.NONE,
    var repository: IPeriodRepository =  IPeriodRepository.NONE
)
