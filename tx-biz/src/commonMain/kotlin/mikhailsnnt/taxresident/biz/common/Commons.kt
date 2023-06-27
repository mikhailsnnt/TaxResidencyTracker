package mikhailsnnt.taxresident.biz.common

import kotlinx.datetime.toLocalDate
import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.TxError
import mikhailsnnt.taxresident.common.enums.TxState
import org.lighthousegames.logging.logging

val log = logging()

val internalError: suspend TxContext.(Throwable) -> Unit ={
    log.error { "Exception caught $it" }
    state = TxState.FAILING
}


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