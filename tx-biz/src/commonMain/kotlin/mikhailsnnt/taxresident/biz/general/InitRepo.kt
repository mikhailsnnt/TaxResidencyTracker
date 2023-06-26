package mikhailsnnt.taxresident.biz.general

import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.enums.TxWorkMode
import mikhailsnnt.taxresident.lib.cor.ICorChainDsl
import mikhailsnnt.taxresident.lib.cor.worker


fun ICorChainDsl<TxContext>.initRepo(title: String) = worker{
    this.title = title
    handle {
        when(workMode){
            TxWorkMode.PROD -> settings.periodRepositoryProd
            TxWorkMode.TEST -> settings.periodRepositoryTest
            else -> null
        }?.let {
            repository = it
        }
    }
}