package mikhailsnnt.taxresident.biz.common

import kotlinx.datetime.toLocalDate
import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.model.TxError
import mikhailsnnt.taxresident.common.model.enums.TxState

fun TxContext.fail(vararg errors: TxError) {
    this.errors += errors
    state = TxState.FAILING
}

fun isDateParsable(date: String) =
    try {
        date.toLocalDate()
        true
    } catch (e: IllegalArgumentException) {
        false
    }

fun errorValidating(
    field: String,
    violationCode: String,
    description: String,
) = TxError("validation-$field-$violationCode","validation", field, description)