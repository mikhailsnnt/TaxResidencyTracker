package mikhail.snnt.taxresident.api.multi.v1.response

import mikhail.snnt.taxresident.api.v1.models.IResponse
import mikhail.snnt.taxresident.api.v1.models.PeriodCreateResponse


object PeriodCreateResponseStrategy : IResponseStrategy {
    override val discriminator = "create"
    override val clazz = PeriodCreateResponse::class
    override val serializer = PeriodCreateResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is PeriodCreateResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}