package mikhail.snnt.taxresident.api.multi.v2

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import mikhail.snnt.taxresident.api.multi.v1.RequestSerializer
import mikhail.snnt.taxresident.api.multi.v1.RequestSerializerInternal
import mikhail.snnt.taxresident.api.multi.v1.ResponseSerializer
import mikhail.snnt.taxresident.api.multi.v1.ResponseSerializerInternal
import mikhail.snnt.taxresident.api.v1.models.*

@OptIn(ExperimentalSerializationApi::class)
val apiV2Mapper = Json{
    classDiscriminator = "_"
    serializersModule = SerializersModule{
        polymorphicDefaultSerializer(IRequest::class){
            when(it){
                is PeriodCreateRequest -> RequestSerializerInternal(PeriodCreateRequest.serializer()) as SerializationStrategy<IRequest>
                is PeriodReadRequest -> RequestSerializerInternal(PeriodReadRequest.serializer()) as SerializationStrategy<IRequest>
                is PeriodDeleteRequest -> RequestSerializerInternal(PeriodDeleteRequest.serializer()) as SerializationStrategy<IRequest>
                is PeriodUpdateRequest -> RequestSerializerInternal(PeriodUpdateRequest.serializer()) as SerializationStrategy<IRequest>
                is TaxResidencyRequest -> RequestSerializerInternal(TaxResidencyRequest.serializer()) as SerializationStrategy<IRequest>
                else -> null
            }
        }
        polymorphicDefault(IRequest::class){
            RequestSerializer
        }

        polymorphicDefaultSerializer(IResponse::class){
            @Suppress("unchecked")
            when(it){
                is PeriodCreateResponse -> ResponseSerializerInternal(PeriodCreateResponse.serializer()) as SerializationStrategy<IResponse>
                is PeriodReadResponse -> ResponseSerializerInternal(PeriodReadResponse.serializer()) as SerializationStrategy<IResponse>
                is PeriodDeleteResponse -> ResponseSerializerInternal(PeriodDeleteResponse.serializer()) as SerializationStrategy<IResponse>
                is PeriodUpdateResponse -> ResponseSerializerInternal(PeriodUpdateResponse.serializer()) as SerializationStrategy<IResponse>
                is TaxResidencyResponse -> ResponseSerializerInternal(TaxResidencyResponse.serializer()) as SerializationStrategy<IResponse>
                else -> null
            }
        }

        polymorphicDefault(IResponse::class) {
            ResponseSerializer
        }

        contextual(RequestSerializer)

        contextual(ResponseSerializer)
    }
}

