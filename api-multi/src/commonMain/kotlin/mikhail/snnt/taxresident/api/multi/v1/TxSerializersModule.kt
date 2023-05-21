package mikhail.snnt.taxresident.api.multi.v1

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import mikhail.snnt.taxresident.api.v1.models.IRequest
import mikhail.snnt.taxresident.api.v1.models.IResponse


val TxSerializersModule =
    SerializersModule {
        polymorphic(IRequest::class){
            default {
                RequestSerializer
            }
        }
        polymorphicDefault(IResponse::class){
            ResponseSerializer
        }
    }
