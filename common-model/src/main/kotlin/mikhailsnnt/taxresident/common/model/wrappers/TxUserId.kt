package mikhailsnnt.taxresident.common.model.wrappers

@JvmInline
value class TxUserId(
    private val id: String
){

    override fun toString() = id

    companion object{
        val NONE = TxUserId("")
    }
}