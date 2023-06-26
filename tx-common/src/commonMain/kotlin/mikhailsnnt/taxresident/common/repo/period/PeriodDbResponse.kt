package mikhailsnnt.taxresident.common.repo.period

import mikhailsnnt.taxresident.common.TxError
import mikhailsnnt.taxresident.common.model.TxPeriod
import mikhailsnnt.taxresident.common.repo.IDbResponse

data class PeriodDbResponse(
    override val data: TxPeriod?,
    override val isSuccess: Boolean,
    override val errors: List<TxError> = emptyList()
) : IDbResponse<TxPeriod> {
    @Suppress("unused")
    companion object {
        fun success(data: TxPeriod? = null) = PeriodDbResponse(data, true)
        fun error(error: TxError) = PeriodDbResponse(null, false, listOf(error))
        fun error(errors: List<TxError>) = PeriodDbResponse(null, false, errors)
        fun Throwable.toPeriodDbError() = error(
            TxError(
                code = "internal_db_exception",
                group = "db_error",
                exception = this
            )
        )
    }
}