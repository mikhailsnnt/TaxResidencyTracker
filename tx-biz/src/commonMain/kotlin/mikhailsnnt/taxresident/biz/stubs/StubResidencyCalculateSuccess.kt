package mikhailsnnt.taxresident.biz.stubs

import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.model.enums.TxState
import mikhailsnnt.taxresident.common.model.enums.TxStubCase
import mikhailsnnt.taxresident.lib.cor.ICorChainDsl
import mikhailsnnt.taxresident.lib.cor.worker


fun ICorChainDsl<TxContext>.stubResidencyCalculateSuccess(title: String) = worker {
    this.title = title
    on{
        stubCase == TxStubCase.SUCCESS && state == TxState.RUNNING
    }
    handle{
        state = TxState.FINISHING
        taxResidencyResponse = Stubs.residencyInfo()
    }
}