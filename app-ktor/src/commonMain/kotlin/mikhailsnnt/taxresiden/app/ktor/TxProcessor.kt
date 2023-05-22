package mikhailsnnt.taxresiden.app.ktor

import mikhailsnnt.taxresident.common.TxContext

interface TxProcessor{
    fun process(txContext: TxContext)

    companion object{
        val NONE = object : TxProcessor {
            override fun process(txContext: TxContext) {
                throw NotImplementedError("TxProcessor.NONE methods not implemented")
            }
        }
    }
}