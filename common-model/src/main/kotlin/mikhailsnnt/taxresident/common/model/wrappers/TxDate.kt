package mikhailsnnt.taxresident.common.model.wrappers

@JvmInline
/***
 * @param date date in format YYYY-MM-dd
 */
value class TxDate(
    private val date: String
){
    override fun toString() = date

    companion object{
        val NONE = TxDate("0000-00-00")
    }
}