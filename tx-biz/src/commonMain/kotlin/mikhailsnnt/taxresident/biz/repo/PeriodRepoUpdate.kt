package mikhailsnnt.taxresident.biz.repo

import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.enums.TxState
import mikhailsnnt.taxresident.common.repo.period.PeriodDbRequest
import mikhailsnnt.taxresident.lib.cor.ICorChainDsl
import mikhailsnnt.taxresident.lib.cor.worker

fun ICorChainDsl<TxContext>.repoUpdate(title: String) = worker {
    this.title = title
    description = "Обновление периода БД"
    on { state == TxState.RUNNING }
    handle {
        val request = PeriodDbRequest(userId, periodRequest)
        val result = repository.update(request)
        val resultPeriod = result.data
        if (result.isSuccess && resultPeriod != null)
            singlePeriodResponse = resultPeriod
        else {
            state = TxState.FAILING
            errors.addAll(result.errors)
        }
    }
}