package mikhailsnnt.taxresident.biz

import mikhailsnnt.taxresident.biz.general.initRepo
import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.lib.cor.rootChain
import mikhailsnnt.taxresident.biz.general.operation
import mikhailsnnt.taxresident.biz.general.prepareResult
import mikhailsnnt.taxresident.biz.repo.repoCreate
import mikhailsnnt.taxresident.biz.repo.repoDelete
import mikhailsnnt.taxresident.biz.repo.repoRead
import mikhailsnnt.taxresident.biz.repo.repoUpdate
import mikhailsnnt.taxresident.biz.stubs.*
import mikhailsnnt.taxresident.biz.validation.accessValidation
import mikhailsnnt.taxresident.biz.validation.validateDateFormat
import mikhailsnnt.taxresident.biz.validation.validateStringFieldNotEmpty
import mikhailsnnt.taxresident.biz.validation.validation
import mikhailsnnt.taxresident.common.TxProcessorSettings
import mikhailsnnt.taxresident.common.enums.TxCommand
import mikhailsnnt.taxresident.common.enums.TxState
import mikhailsnnt.taxresident.lib.cor.worker

class TxProcessor(private val settings: TxProcessorSettings) {

    suspend fun exec(context: TxContext) = businessChain.exec(
        context.also { it.settings = this@TxProcessor.settings }
    )

    companion object {
        @Suppress("DuplicatedCode")
        private val businessChain = rootChain<TxContext> {
            worker("initStatus") { this.state = TxState.RUNNING;  this.userId = "userId"}
            initRepo("Init repo based on work mode")
            operation("Save period", TxCommand.CREATE) {
                stub("Process create request stubs") {
                    stubPeriodCreateSuccess("Stub period create success")
                    stubValidationBadFormat("Stub period update bad request")
                }
                validation {
                    validateStringFieldNotEmpty("startDate") { periodRequest.startDate }
                    validateDateFormat("startDate") { periodRequest.startDate }
                    validateDateFormat("endDate") { periodRequest.endDate }
                    validateStringFieldNotEmpty("endDate") { periodRequest.endDate }
                }
                repoCreate("Creating period")
            }

            operation("Read periods", TxCommand.READ) {
                stub("Process read stubs") {
                    stubPeriodsReadSuccess("Stub periods read success")
                }
                repoRead("Reading periods")
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
                accessValidation("Check user has access to period")
                repoUpdate("Period repo update")
            }

            operation("Delete period", TxCommand.DELETE) {
                stub("Process period delete request stubs") {
                    stubPeriodNotFound("Stub period to delete not found")
                    stubPeriodDeleteSuccess("Stub period delete success")
                }
                validation {
                    validateStringFieldNotEmpty("period.id") { periodRequest.id }
                }
                accessValidation("Check user has access to period")
                repoDelete("Period repo delete")
            }
            prepareResult("Complete buziness chain")
        }.build()
    }
}