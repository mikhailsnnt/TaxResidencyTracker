package mikhailsnnt.taxresident.biz.general

import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.model.enums.TxCommand
import mikhailsnnt.taxresident.common.model.enums.TxState
import mikhailsnnt.taxresident.lib.cor.ICorChainDsl
import mikhailsnnt.taxresident.lib.cor.chain


fun ICorChainDsl<TxContext>.operation(title: String, command: TxCommand, block: ICorChainDsl<TxContext>.() -> Unit) = chain{
    this.title = title
    block()
    on {
        this.command == command && state == TxState.RUNNING
    }
}