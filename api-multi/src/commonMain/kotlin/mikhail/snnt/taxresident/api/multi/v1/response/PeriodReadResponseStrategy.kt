package mikhail.snnt.taxresident.api.multi.v1.response

import mikhail.snnt.taxresident.api.v1.models.IResponse
import mikhail.snnt.taxresident.api.v1.models.PeriodReadResponse

object PeriodReadResponseStrategy : IResponseStrategy {
    override val discriminator = "read"
    override val clazz = PeriodReadResponse::class
    override val serializer = PeriodReadResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is PeriodReadResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}