package mikhailsnnt.taxresident.biz.validation

import mikhailsnnt.taxresident.biz.common.errorValidating
import mikhailsnnt.taxresident.biz.common.fail
import mikhailsnnt.taxresident.biz.common.isDateParsable
import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.lib.cor.ICorChainDsl
import mikhailsnnt.taxresident.lib.cor.worker

fun ICorChainDsl<TxContext>.validateDateFormat(fieldName: String, dateProvider: TxContext.()->(String)) = worker {
    handle{
        if(!isDateParsable(dateProvider(this)))
            fail(errorValidating(fieldName, "badDateFormat","$fieldName is bad format"))
    }
}