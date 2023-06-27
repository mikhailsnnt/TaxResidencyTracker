package mikhailsnnt.taxresident.common.repo.period

import mikhailsnnt.taxresident.common.model.TxPeriod
import mikhailsnnt.taxresident.common.model.wrappers.TxUserId


data class PeriodDbRequest(
    val userId: TxUserId,
    val period: TxPeriod
)