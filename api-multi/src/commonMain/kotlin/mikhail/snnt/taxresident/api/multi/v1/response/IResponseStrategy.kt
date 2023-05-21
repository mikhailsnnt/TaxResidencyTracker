package mikhail.snnt.taxresident.api.multi.v1.response

import mikhail.snnt.taxresident.api.multi.v1.IApiStrategy
import mikhail.snnt.taxresident.api.v1.models.IResponse

sealed interface IResponseStrategy : IApiStrategy<IResponse> {
    companion object{
        private val members: List<IResponseStrategy> = listOf(
            PeriodCreateResponseStrategy,
            PeriodReadResponseStrategy,
            PeriodUpdateResponseStrategy,
            PeriodDeleteResponseStrategy,
            TaxResidencyResponceStrategy
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClass = members.associateBy { it.clazz }
    }
}