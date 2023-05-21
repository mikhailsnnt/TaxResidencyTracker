package mikhail.snnt.taxresident.api.multi.v1.request

import kotlinx.serialization.KSerializer
import mikhail.snnt.taxresident.api.v1.models.IRequest
import mikhail.snnt.taxresident.api.v1.models.PeriodReadRequest

object PeriodReadRequestStrategy : IRequestStrategy {
    override val discriminator = "read"
    override val clazz = PeriodReadRequest::class
    override val serializer: KSerializer<out IRequest> = PeriodReadRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is PeriodReadRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}