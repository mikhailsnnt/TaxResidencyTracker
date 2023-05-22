package mikhail.snnt.taxresident.api.multi.v1.response

import mikhail.snnt.taxresident.api.v1.models.IResponse
import mikhail.snnt.taxresident.api.v1.models.PeriodUpdateResponse

object PeriodUpdateResponseStrategy : IResponseStrategy {
    override val discriminator = "update"
    override val clazz = PeriodUpdateResponse::class
    override val serializer = PeriodUpdateResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is PeriodUpdateResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}