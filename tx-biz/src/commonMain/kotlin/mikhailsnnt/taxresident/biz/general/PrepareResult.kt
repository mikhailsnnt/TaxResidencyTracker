package mikhailsnnt.taxresident.biz.general

import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.model.enums.TxState
import mikhailsnnt.taxresident.lib.cor.ICorChainDsl
import mikhailsnnt.taxresident.lib.cor.worker


fun ICorChainDsl<TxContext>.prepareResult(title: String) = worker{
    this.title = title
    handle {
        if(state == TxState.RUNNING || state ==  TxState.FINISHING)
            state = TxState.COMPLETED
    }
}