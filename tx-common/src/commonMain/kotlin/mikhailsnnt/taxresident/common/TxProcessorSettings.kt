package mikhailsnnt.taxresident.common

import mikhailsnnt.taxresident.common.repo.period.IPeriodRepository


data class TxProcessorSettings(
    val periodRepositoryProd: IPeriodRepository,
    val periodRepositoryTest: IPeriodRepository,
){
    companion object{
        val NONE = TxProcessorSettings(
            IPeriodRepository.NONE,
            IPeriodRepository.NONE
        )
    }
}