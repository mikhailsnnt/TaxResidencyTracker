package mikhailsnnt.taxresident.api.jackson

import mikhail.snnt.taxresident.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertEquals


internal class ApiMapperTest{

    @Test
    fun checkSampleRequestsDeserialize(){
        sampleRequests.forEach {
            assertEquals(
                it.key,
                apiMapper.readValue(it.value, IRequest::class.java)
            )
        }
    }

    @Test
    fun checkSampleResponseDeserialize(){
        sampleResponses.forEach {
            assertEquals(
                it.key,
                apiMapper.readValue(it.value, IResponse::class.java)
            )
        }
    }

    @Test
    fun checkSerializeAndDeserialize(){
        val request = PeriodDeleteRequest(
            requestType = "DELETE",
            requestId = "someId",
            debug = null,
            period = PeriodDeleteObject(
                "periodId"
            )
        )
        val requestJson = apiMapper.writeValueAsString(request)

        assertEquals(
            request,
            apiMapper.readValue(requestJson, IRequest::class.java)
        )
    }



    private val sampleRequests = mapOf(
        PeriodCreateRequest(
            requestType = "create",
            requestId = "id",
            debug = null,
            period = PeriodCreateObject(
                startDate = "2017-01-01",
                endDate = "2018-01-01"
            )
        ) to "{\"requestType\":\"create\",\"requestId\":\"id\",\"debug\":null,\"period\":{\"startDate\":\"2017-01-01\",\"endDate\":\"2018-01-01\"}}",
        PeriodUpdateRequest(
            requestType = "update",
            requestId = "id",
            debug = null,
            period = PeriodUpdateObject(
                startDate = "2017-01-01",
                endDate = "2018-01-01"
            )
        ) to "{\"requestType\":\"update\",\"requestId\":\"id\",\"debug\":null,\"period\":{\"startDate\":\"2017-01-01\",\"endDate\":\"2018-01-01\"}}",
    )


    private val sampleResponses = mapOf(
        PeriodCreateResponse(
            responseType = "create",
            requestId = "id",
            result = ResponseResult.SUCCESS,
            errors = emptyList(),
            period = PeriodResponseObject(
                startDate = "",
                endDate = "",
                id = "perId"
            )
        ) to "{\"responseType\":\"create\",\"responseType\":\"create\",\"requestId\":\"id\",\"result\":\"success\",\"errors\":[],\"period\":{\"startDate\":\"\",\"endDate\":\"\",\"id\":\"perId\"}}"
    )
}