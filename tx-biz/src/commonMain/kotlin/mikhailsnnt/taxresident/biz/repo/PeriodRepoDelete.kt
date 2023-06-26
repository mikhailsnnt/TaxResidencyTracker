package mikhailsnnt.taxresident.biz.repo

import mikhailsnnt.taxresident.biz.common.internalError
import mikhailsnnt.taxresident.biz.common.log
import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.enums.TxState
import mikhailsnnt.taxresident.common.repo.period.PeriodIdRequest
import mikhailsnnt.taxresident.lib.cor.ICorChainDsl
import mikhailsnnt.taxresident.lib.cor.worker

fun ICorChainDsl<TxContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление периодов из БД"
    on { state == TxState.RUNNING }
    except(internalError)
    handle {
        val request = PeriodIdRequest(userId)
        log.info { "Deleting period $request" }
        val result = repository.delete(request)
        if (!result.isSuccess){
            state = TxState.FAILING
            errors.addAll(result.errors)
        }
    }
}