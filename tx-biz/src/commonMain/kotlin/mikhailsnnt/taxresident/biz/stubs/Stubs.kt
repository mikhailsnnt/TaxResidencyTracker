package mikhailsnnt.taxresident.biz.stubs

import mikhailsnnt.taxresident.common.model.TxPeriod
import mikhailsnnt.taxresident.common.model.TxResidencyInfo

object Stubs {
    fun multiplePeriods() = listOf(
        TxPeriod("id1","2023-01-01","2023-02-01"),
        TxPeriod("id2","2023-01-01","2023-02-01")
    )

    fun singlePeriod() = TxPeriod("id1","2023-01-01","2023-02-01")

    fun residencyInfo() = TxResidencyInfo(
        willLoseResidency = true,
        dateOfResidencyLoss = "2024-01-01"
    )
}