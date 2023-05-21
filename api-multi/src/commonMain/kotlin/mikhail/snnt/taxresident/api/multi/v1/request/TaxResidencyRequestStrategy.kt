package mikhail.snnt.taxresident.api.multi.v1.request

import kotlinx.serialization.KSerializer
import mikhail.snnt.taxresident.api.v1.models.IRequest
import mikhail.snnt.taxresident.api.v1.models.TaxResidencyRequest

object TaxResidencyRequestStrategy : IRequestStrategy {
    override val discriminator = "calculateResidency"
    override val clazz = TaxResidencyRequest::class
    override val serializer: KSerializer<out IRequest> = TaxResidencyRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is TaxResidencyRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}