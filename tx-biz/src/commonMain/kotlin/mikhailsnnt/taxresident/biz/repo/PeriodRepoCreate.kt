package mikhailsnnt.taxresident.biz.repo

import mikhailsnnt.taxresident.biz.common.log
import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.enums.TxState
import mikhailsnnt.taxresident.common.repo.period.PeriodDbRequest
import mikhailsnnt.taxresident.lib.cor.ICorChainDsl
import mikhailsnnt.taxresident.lib.cor.worker

fun ICorChainDsl<TxContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Добавление периода в БД"
    on { state == TxState.RUNNING }
    handle {
        val request = PeriodDbRequest(userId, periodRequest)
        log.info { "Creating period $request" }
        val result = repository.create(request)
        val resultAd = result.data
        if (result.isSuccess && resultAd != null)
            singlePeriodResponse = resultAd
        else {
            state = TxState.FAILING
            errors.addAll(result.errors)
        }
    }
}