package mikhail.snnt.taxresident.api.multi.v1.request

import kotlinx.serialization.KSerializer
import mikhail.snnt.taxresident.api.v1.models.IRequest
import mikhail.snnt.taxresident.api.v1.models.PeriodDeleteRequest

object PeriodDeleteRequestStrategy : IRequestStrategy {
    override val discriminator = "delete"
    override val clazz = PeriodDeleteRequest::class
    override val serializer: KSerializer<out IRequest> = PeriodDeleteRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is PeriodDeleteRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}