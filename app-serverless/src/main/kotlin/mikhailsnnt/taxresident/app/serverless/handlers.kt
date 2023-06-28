package mikhailsnnt.taxresident.app.serverless

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.runBlocking
import mikhail.snnt.taxresident.api.v1.models.*
import mikhailsnnt.taxresident.biz.TxProcessor
import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.TxProcessorSettings
import mikhailsnnt.taxresident.common.mappers.fromTransport
import mikhailsnnt.taxresident.common.mappers.toTransport
import mikhailsnnt.taxresident.repo.ydb.PeriodYdbRepository
import mikhailsnnt.taxresident.app.serverless.model.Request
import mikhailsnnt.taxresident.app.serverless.model.Response
import yandex.cloud.sdk.functions.Context
import java.util.*


inline fun <reified Q: IRequest> processCall(input: Request, context: Context): Response = runBlocking{
    val txContext = TxContext(requestId = context.requestId)
    val request = input.toTransportModel<Q>()

    txContext.fromTransport(request)

    processor.exec(txContext)

    txContext.toTransport().toResponse()
}


val objectMapper: ObjectMapper = jacksonObjectMapper().findAndRegisterModules()
inline fun <reified T> Request.toTransportModel(): T =
    if (isBase64Encoded) {
        objectMapper.readValue(Base64.getDecoder().decode(body))
    } else {
        objectMapper.readValue(body)
    }

fun IResponse.toResponse(): Response =
    Response(
        200,
        mapOf("Content-Type" to "application/json"),
        objectMapper.writeValueAsString(this)
    )


val processor = TxProcessor(
    TxProcessorSettings(
        periodRepositoryProd = PeriodYdbRepository(""),
        periodRepositoryTest = PeriodYdbRepository("")
    )
)