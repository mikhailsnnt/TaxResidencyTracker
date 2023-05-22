package mikhail.snnt.taxresident.api.multi.v1

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*
import mikhail.snnt.taxresident.api.multi.v1.request.IRequestStrategy
import mikhail.snnt.taxresident.api.v1.models.IRequest

val RequestSerializer = RequestSerializerInternal(RequestSerializerBase)

private object RequestSerializerBase : JsonContentPolymorphicSerializer<IRequest>(IRequest::class){
    const val discriminator = "requestType"

    override fun selectDeserializer(element: JsonElement): KSerializer<out IRequest> {
        val discriminatorValue = element.jsonObject[discriminator]?.jsonPrimitive?.content
        return IRequestStrategy.membersByDiscriminator[discriminatorValue]
            ?.serializer
            ?: throw SerializationException(
                "Unknown request discriminator $discriminator value $discriminatorValue"
            )
    }
}

class RequestSerializerInternal<T : IRequest>(private val serializer: KSerializer<T>): KSerializer<T> by serializer{
    override fun serialize(encoder: Encoder, value: T) = IRequestStrategy
        .membersByClass[value::class]
        ?.fillDiscriminator(value)
        ?.let { serializer.serialize(encoder, it) }
        ?: throw SerializationException("Unknown IRequest to serialize ${value::class}")
}