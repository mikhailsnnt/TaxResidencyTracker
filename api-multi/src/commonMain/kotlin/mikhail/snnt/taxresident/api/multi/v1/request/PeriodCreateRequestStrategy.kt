package mikhail.snnt.taxresident.api.multi.v1.request

import kotlinx.serialization.KSerializer
import mikhail.snnt.taxresident.api.v1.models.IRequest
import mikhail.snnt.taxresident.api.v1.models.PeriodCreateRequest

object PeriodCreateRequestStrategy : IRequestStrategy {
    override val discriminator = "create"
    override val clazz = PeriodCreateRequest::class
    override val serializer: KSerializer<out IRequest> = PeriodCreateRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is PeriodCreateRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}