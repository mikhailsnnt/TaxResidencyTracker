package mikhailsnnt.taxresident.biz.stubs

import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.enums.TxState
import mikhailsnnt.taxresident.common.enums.TxStubCase
import mikhailsnnt.taxresident.lib.cor.ICorChainDsl
import mikhailsnnt.taxresident.lib.cor.worker

fun ICorChainDsl<TxContext>.stubPeriodCreateSuccess(title: String) = worker {
    this.title = title
    on{
        stubCase == TxStubCase.SUCCESS && state == TxState.RUNNING
    }
    handle{
        state = TxState.FINISHING
        singlePeriodResponse = Stubs.singlePeriod().let { newPeriod ->
            periodRequest.startDate.takeIf { it.isNotBlank() }?.let{ newPeriod.startDate = it}
            periodRequest.endDate.takeIf { it.isNotBlank() }?.let{ newPeriod.endDate = it}
            newPeriod
        }
    }
}