package mikhailsnnt.taxresident.biz.stubs

import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.model.enums.TxState
import mikhailsnnt.taxresident.common.model.enums.TxWorkMode
import mikhailsnnt.taxresident.lib.cor.ICorChainDsl
import mikhailsnnt.taxresident.lib.cor.chain


fun ICorChainDsl<TxContext>.stub(title: String, block: ICorChainDsl<TxContext>.() -> Unit) = chain{
    this.title = title
    block()
    on {
        workMode == TxWorkMode.STUB && state == TxState.RUNNING
    }
}