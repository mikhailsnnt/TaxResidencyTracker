package mikhailsnnt.taxresident.common.model

import mikhailsnnt.taxresident.common.model.wrappers.TxDate
import mikhailsnnt.taxresident.common.model.wrappers.TxUserId

data class TxPeriod(
    var id: String = "",
    var startDate: TxDate = "",
    var endDate: TxDate =  ""
)
