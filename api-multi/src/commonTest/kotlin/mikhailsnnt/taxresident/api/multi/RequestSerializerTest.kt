package mikhailsnnt.taxresident.api.multi

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import mikhail.snnt.taxresident.api.v1.models.*
import mikhail.snnt.taxresident.api.multi.v1.TxSerializersModule
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializerTest {

    lateinit var json : Json

    @BeforeTest
    fun initJsonModule(){
        json = Json{
            serializersModule = TxSerializersModule
        }
    }

    @Test
    fun shouldSerialize() {
        sampleSerializationResults().forEach { (req, assertRegexes) ->
            val resultJson = json.encodeToJsonElement(req).toString()
            println(resultJson)
            assertRegexes.forEach {
                assertContains(resultJson, it)
            }
        }
    }

    @Test
    fun shouldDeserializeRequest(){
        sampleDeserializationRequestResults().forEach { (testJson, testObject) ->
            val actual = json.decodeFromJsonElement<IRequest>(json.parseToJsonElement(testJson))
            assertEquals(testObject, actual)
        }
    }

    @Test
    fun shouldDeserializeResponse(){
        sampleDeserializationResponseResults().forEach { (testJson, testObject) ->
            val actual = json.decodeFromJsonElement<IResponse>(json.parseToJsonElement(testJson))
            assertEquals(testObject, actual)
        }
    }


    private fun sampleSerializationResults() = listOf (PeriodCreateRequest(
        requestType = "create",
        requestId = "requestId",
        period = PeriodCreateObject(
            startDate = "2017-01-01",
            endDate = "2017-02-01"
        )) to listOf(Regex("\"requestType\":\\s*\"create\""), Regex("\"requestType\":\\s*\"create\""))
    )


    private fun sampleDeserializationRequestResults() = listOf(
        "{\"requestType\":\"delete\", \"requestId\":\"requestId\",\"deleteObject\":{\"id\":\"0\"}}"
        to PeriodDeleteRequest(
            requestType = "delete",
            requestId = "requestId",
            deleteObject = PeriodDeleteObject(
                id = "0"
            )
        )
    )

    private fun sampleDeserializationResponseResults() = listOf(
        "{\"responseType\":\"delete\", \"requestId\":\"reqId\",\"result\":\"success\"}"
                to PeriodDeleteResponse(
            responseType = "delete",
            requestId = "reqId",
            result = ResponseResult.SUCCESS
        )
    )
}