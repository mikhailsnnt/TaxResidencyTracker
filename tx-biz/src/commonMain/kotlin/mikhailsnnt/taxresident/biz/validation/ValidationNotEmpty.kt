package mikhailsnnt.taxresident.biz.validation

import mikhailsnnt.taxresident.biz.common.errorValidating
import mikhailsnnt.taxresident.biz.common.fail
import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.lib.cor.ICorChainDsl
import mikhailsnnt.taxresident.lib.cor.worker

fun ICorChainDsl<TxContext>.validateStringFieldNotEmpty(fieldName: String, fieldProvider: TxContext.()->String) = worker {
    handle{
        if(fieldProvider(this).isBlank())
            fail(errorValidating(fieldName, "emptyInput","Required $fieldName is empty"))
    }
}