package mikhail.snnt.taxresident.api.multi.v1

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import mikhail.snnt.taxresident.api.v1.models.IResponse
import mikhail.snnt.taxresident.api.multi.v1.response.IResponseStrategy

val ResponseSerializer = ResponseSerializerInternal(ResponseSerializerBase)

private object ResponseSerializerBase : JsonContentPolymorphicSerializer<IResponse>(IResponse::class){
    const val discriminator = "responseType"

    override fun selectDeserializer(element: JsonElement): KSerializer<out IResponse> {
        val discriminatorValue = element.jsonObject[discriminator]?.jsonPrimitive?.content
        return IResponseStrategy.membersByDiscriminator[discriminatorValue]
            ?.serializer
            ?: throw SerializationException(
                "Unknown request discriminator $discriminator value $discriminatorValue"
            )
    }
}

class ResponseSerializerInternal<T : IResponse>(private val serializer: KSerializer<T>): KSerializer<T> by serializer{
    override fun serialize(encoder: Encoder, value: T) = IResponseStrategy
        .membersByClass[value::class]
        ?.fillDiscriminator(value)
        ?.let { serializer.serialize(encoder, it) }
        ?: throw SerializationException("Unknown IResponse to serialize ${value::class}")
}