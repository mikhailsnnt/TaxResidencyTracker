package mikhail.snnt.taxresident.api.multi.v1.response

import mikhail.snnt.taxresident.api.v1.models.IResponse
import mikhail.snnt.taxresident.api.v1.models.TaxResidencyResponse

object TaxResidencyResponceStrategy : IResponseStrategy {
    override val discriminator = "calculateResidency"
    override val clazz = TaxResidencyResponse::class
    override val serializer = TaxResidencyResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is TaxResidencyResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}