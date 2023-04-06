package mikhailsnnt.taxresident.common.model

data class TxError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
)