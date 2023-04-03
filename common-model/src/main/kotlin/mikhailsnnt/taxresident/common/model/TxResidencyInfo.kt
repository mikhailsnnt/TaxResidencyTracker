package mikhailsnnt.taxresident.common.model

import mikhailsnnt.taxresident.common.model.wrappers.TxDate

data class TxResidencyInfo (
    var willLoseResidency: Boolean = false,
    var dateOfResidencyLoss: TxDate = TxDate.NONE
)