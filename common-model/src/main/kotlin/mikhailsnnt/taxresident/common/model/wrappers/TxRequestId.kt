package mikhailsnnt.taxresident.common.model.wrappers

@JvmInline
value class TxRequestId(
    private val id: String
){

    override fun toString() = id

    companion object{
        val NONE = TxRequestId("")
    }
}