package mikhailsnnt.taxresident.common.repo.period

import mikhailsnnt.taxresident.common.model.TxError
import mikhailsnnt.taxresident.common.model.TxPeriod
import mikhailsnnt.taxresident.common.repo.IDbResponse

data class PeriodsDbResponse(
    override val data: List<TxPeriod>?,
    override val isSuccess: Boolean,
    override val errors: List<TxError> = emptyList()
) : IDbResponse<List<TxPeriod>> {
    @Suppress("unused")
    companion object {
        fun success(data: List<TxPeriod>) = PeriodsDbResponse(data, true)
        fun error(error: TxError) = PeriodsDbResponse(null, false, listOf(error))
        fun error(errors: List<TxError>) = PeriodsDbResponse(null, false, errors)
    }
}