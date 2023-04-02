package mikhailsnnt.taxresident.api.jackson

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import mikhail.snnt.taxresident.api.v1.models.IRequest
import mikhail.snnt.taxresident.api.v1.models.IResponse

val apiMapper = ObjectMapper().apply {
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
}

@Suppress("UNCHECKED_CAST")
fun <T : IRequest> requestDeserialize(json: String) =
    apiMapper.readValue(json, IRequest::class.java) as T


@Suppress("UNCHECKED_CAST")
fun <T : IResponse> responseDeserialize(json: String) =
    apiMapper.readValue(json, IResponse::class.java) as T


fun responseSerialize(response: IResponse) =
    apiMapper.writeValueAsString(response)