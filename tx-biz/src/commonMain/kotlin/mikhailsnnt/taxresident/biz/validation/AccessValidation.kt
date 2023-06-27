package mikhailsnnt.taxresident.biz.validation

import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.TxError
import mikhailsnnt.taxresident.common.enums.TxState
import mikhailsnnt.taxresident.common.repo.period.PeriodDbSearchFilter
import mikhailsnnt.taxresident.lib.cor.ICorChainDsl
import mikhailsnnt.taxresident.lib.cor.worker

fun ICorChainDsl<TxContext>.accessValidation(title: String) = worker{
    this.title = title
    description = "Checking user has access to Requested period"
    on {
        state == TxState.RUNNING
    }
    handle{
        if(periodRequest.id != ""){
            val period = repository.read(userId, PeriodDbSearchFilter(periodRequest.id,1))
            if (period.isSuccess && period.data != null){
                if (period.data!!.isEmpty())
                    errors += TxError("PeriodBadId", "BadId", "Period with id: ${periodRequest.id} not found")
            }
            else{
                state = TxState.FAILING
                errors.add(TxError("repo-fail","access-validation-error"))
            }
        }
    }
}