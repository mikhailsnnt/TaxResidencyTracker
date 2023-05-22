package mikhail.snnt.taxresident.api.multi.v1.request

import kotlinx.serialization.KSerializer
import mikhail.snnt.taxresident.api.v1.models.IRequest
import mikhail.snnt.taxresident.api.v1.models.PeriodUpdateRequest

object PeriodUpdateRequestStrategy : IRequestStrategy {
    override val discriminator = "update"
    override val clazz = PeriodUpdateRequest::class
    override val serializer: KSerializer<out IRequest> = PeriodUpdateRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is PeriodUpdateRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}