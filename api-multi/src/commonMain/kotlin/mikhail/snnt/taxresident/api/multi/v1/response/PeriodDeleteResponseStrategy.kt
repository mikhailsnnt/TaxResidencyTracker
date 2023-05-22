package mikhail.snnt.taxresident.api.multi.v1.response

import mikhail.snnt.taxresident.api.v1.models.IResponse
import mikhail.snnt.taxresident.api.v1.models.PeriodDeleteResponse

object PeriodDeleteResponseStrategy : IResponseStrategy {
    override val discriminator = "delete"
    override val clazz = PeriodDeleteResponse::class
    override val serializer = PeriodDeleteResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is PeriodDeleteResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}