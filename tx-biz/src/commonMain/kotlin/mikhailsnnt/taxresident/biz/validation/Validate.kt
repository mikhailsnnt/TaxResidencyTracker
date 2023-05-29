package mikhailsnnt.taxresident.biz.validation

import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.model.enums.TxState
import mikhailsnnt.taxresident.lib.cor.ICorChainDsl
import mikhailsnnt.taxresident.lib.cor.chain

fun ICorChainDsl<TxContext>.validation(block: ICorChainDsl<TxContext>.() -> Unit) = chain{
    this.title = "Validation"
    block()
    on {
        state == TxState.RUNNING
    }
}