package mikhailsnnt.taxresident.common.repo.period

data class PeriodDbSearchFilter(
    val id: String? = null,
    val maxPeriods: Int? = null
){
    companion object{
        val NONE = PeriodDbSearchFilter()
    }
}