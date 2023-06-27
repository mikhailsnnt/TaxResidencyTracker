package mikhailsnnt.taxresident.biz.repo

import mikhailsnnt.taxresident.biz.common.internalError
import mikhailsnnt.taxresident.biz.common.log
import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.enums.TxState
import mikhailsnnt.taxresident.lib.cor.ICorChainDsl
import mikhailsnnt.taxresident.lib.cor.worker


fun ICorChainDsl<TxContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение периодов из БД"
    on { state == TxState.RUNNING }

    except(internalError)

    handle {
        log.info { "Reading periods for user $userId" }

        val result = repository.read(userId)
        val resultPeriods = result.data
        if (result.isSuccess && resultPeriods != null)
            multiplePeriodsResponse.addAll(resultPeriods)
        else {
            log.error { "Period repo.read returned errors: ${result.errors}" }
            state = TxState.FAILING
            errors.addAll(result.errors)
        }
    }
}