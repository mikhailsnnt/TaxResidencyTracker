package mikhailsnnt.taxresident.common.mappers

import mikhail.snnt.taxresident.api.v1.models.*
import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.model.TxPeriod
import mikhailsnnt.taxresident.common.model.enums.TxCommand
import mikhailsnnt.taxresident.common.model.enums.TxState
import mikhailsnnt.taxresident.common.model.wrappers.TxDate
import mikhailsnnt.taxresident.common.model.wrappers.TxPeriodId
import mikhailsnnt.taxresident.common.model.wrappers.TxRequestId
import kotlin.test.Test
import kotlin.test.assertEquals

class MappingTest {

    @Test
    fun shouldMapToTransport() {
        val context = TxContext(
            requestId = TxRequestId("reqId"),
            command = TxCommand.UPDATE,
            state = TxState.COMPLETED,
            singlePeriodResponse = TxPeriod(
                TxPeriodId("perId"),
                startDate = TxDate("2022-01-01"),
                endDate = TxDate("2022-02-02")
            )
        )

        val expectedReponse = PeriodUpdateResponse(
            responseType = "update",
            requestId = "reqId",
            result = ResponseResult.SUCCESS,
            errors = emptyList(),
            period = PeriodResponseObject(
                id = "perId",
                startDate = "2022-01-01",
                endDate = "2022-02-02"
            )
        )

        assertEquals(expectedReponse, context.toTransport())
    }


    @Test
    fun shouldMapFromTransport() {
        val expectedContext = TxContext(
            requestId = TxRequestId("reqId"),
            command = TxCommand.CALCULATE_RESIDENCY,
        )

        val req = TaxResidencyRequest(
            requestType = "calculateResidency",
            requestId = "reqId",
        ) as IRequest

        assertEquals(expectedContext, TxContext().fromTransport(req))
    }
}