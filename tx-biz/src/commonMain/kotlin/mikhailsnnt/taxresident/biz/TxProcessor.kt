package mikhailsnnt.taxresident.biz

import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.model.enums.TxCommand
import mikhailsnnt.taxresident.lib.cor.rootChain
import mikhailsnnt.taxresident.biz.general.operation
import mikhailsnnt.taxresident.biz.general.prepareResult
import mikhailsnnt.taxresident.biz.stubs.*
import mikhailsnnt.taxresident.biz.validation.validateDateFormat
import mikhailsnnt.taxresident.biz.validation.validateStringFieldNotEmpty
import mikhailsnnt.taxresident.biz.validation.validation
import mikhailsnnt.taxresident.common.model.enums.TxState
import mikhailsnnt.taxresident.lib.cor.worker

class TxProcessor {

    suspend fun exec(context: TxContext) = businessChain.exec(context)

    companion object {
        @Suppress("DuplicatedCode")
        private val businessChain = rootChain<TxContext> {
            worker("initStatus") { this.state = TxState.RUNNING }
            operation("Save period", TxCommand.CREATE) {
                stub("Process create request stubs") {
                    stubPeriodCreateSuccess("Stub period create success")
                    stubValidationBadFormat("Stub period update bad request")
                }
                validation{
                    validateStringFieldNotEmpty("startDate") { periodRequest.startDate }
                    validateDateFormat("startDate") { periodRequest.startDate }
                    validateDateFormat("endDate") { periodRequest.endDate }
                    validateStringFieldNotEmpty("endDate") { periodRequest.endDate }
                }
            }

            operation("Read periods", TxCommand.READ) {
                stub("Process read stubs") {
                    stubPeriodsReadSuccess("Stub periods read success")
                }
            }


            operation("Update periods", TxCommand.UPDATE) {
                stub("Process tx period update stubs") {
                    stubPeriodNotFound("Stub period to update not found")
                    stubValidationBadFormat("Stub period update bad request")
                    stubPeriodUpdateSuccess("Stub period update success")
                }
                validation {
                    validateStringFieldNotEmpty("period.id") { periodRequest.id }
                }
            }

            operation("Delete period", TxCommand.DELETE) {
                stub("Process period delete request stubs") {
                    stubPeriodNotFound("Stub period to delete not found")
                    stubPeriodDeleteSuccess("Stub period delete success")
                }
                validation {
                    validateStringFieldNotEmpty("period.id") { periodRequest.id }
                }
            }
            prepareResult("Complete buziness chain")
        }.build()
    }
}