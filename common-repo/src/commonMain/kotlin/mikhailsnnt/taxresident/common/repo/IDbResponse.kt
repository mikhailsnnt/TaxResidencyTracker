package mikhailsnnt.taxresident.common.repo

import mikhailsnnt.taxresident.common.model.TxError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<TxError>
}