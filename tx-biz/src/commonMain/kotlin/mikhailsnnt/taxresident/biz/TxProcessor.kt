package mikhailsnnt.taxresident.biz

import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.model.enums.TxCommand
import mikhailsnnt.taxresident.lib.cor.rootChain
import mikhailsnnt.taxresident.biz.general.operation
import mikhailsnnt.taxresident.biz.general.prepareResult
import mikhailsnnt.taxresident.biz.stubs.*
import mikhailsnnt.taxresident.common.model.enums.TxState
import mikhailsnnt.taxresident.lib.cor.worker

class TxProcessor {

    suspend fun exec(context: TxContext) = businessChain.exec(context)

    companion object{
        private val businessChain = rootChain<TxContext> {
            worker("initStatus") { this.state = TxState.RUNNING   }
            operation("Save period", TxCommand.CREATE){
                stub("Process create request stubs"){
                    stubPeriodCreateSuccess("Stub period create success")
                    stubValidationBadFormat("Stub period update bad request")
                }
            }

            operation("Read periods", TxCommand.READ){
                stub("Process read stubs"){
                    stubPeriodsReadSuccess("Stub periods read success")
                }
            }


            operation("Update periods", TxCommand.UPDATE){
                stub("Process tx period update stubs"){
                    stubPeriodNotFound("Stub period to update not found")
                    stubValidationBadFormat("Stub period update bad request")
                    stubPeriodUpdateSuccess("Stub period update success")
                }
            }

            operation("Delete period", TxCommand.DELETE){
                stub("Process period delete request stubs"){
                    stubPeriodNotFound("Stub period to delete not found")
                    stubPeriodDeleteSuccess("Stub period delete success")
                }
            }
            prepareResult("Complete buziness chain")
        }.build()
    }
}