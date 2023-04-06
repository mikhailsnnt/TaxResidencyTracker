package mikhailsnnt.taxresident.common.model

import mikhailsnnt.taxresident.common.model.wrappers.TxDate
import mikhailsnnt.taxresident.common.model.wrappers.TxPeriodId
import mikhailsnnt.taxresident.common.model.wrappers.TxUserId

data class TxPeriod(
    var id: TxPeriodId = TxPeriodId.NONE,
    var startDate: TxDate = TxDate.NONE,
    var endDate: TxDate = TxDate.NONE,
    var userId: TxUserId = TxUserId.NONE
)
