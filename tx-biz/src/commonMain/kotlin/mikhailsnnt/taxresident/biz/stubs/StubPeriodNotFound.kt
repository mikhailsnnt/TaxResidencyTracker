package mikhailsnnt.taxresident.biz.stubs

import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.model.TxError
import mikhailsnnt.taxresident.common.model.enums.TxState
import mikhailsnnt.taxresident.common.model.enums.TxStubCase
import mikhailsnnt.taxresident.lib.cor.ICorChainDsl
import mikhailsnnt.taxresident.lib.cor.worker


fun ICorChainDsl<TxContext>.stubPeriodNotFound(title: String) = worker {
    this.title = title
    on{
        stubCase == TxStubCase.NOT_FOUND && state == TxState.RUNNING
    }
    handle{
        state = TxState.FAILING
        errors += TxError("PeriodBadId", "BadId", "Period with id: ${periodRequest.id} not found")
    }
}