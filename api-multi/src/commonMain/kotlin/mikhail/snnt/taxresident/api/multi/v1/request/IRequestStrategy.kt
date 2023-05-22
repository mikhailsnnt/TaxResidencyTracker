package mikhail.snnt.taxresident.api.multi.v1.request

import mikhail.snnt.taxresident.api.multi.v1.IApiStrategy
import mikhail.snnt.taxresident.api.v1.models.IRequest

sealed interface IRequestStrategy: IApiStrategy<IRequest> {
    companion object {
        private val members: List<IRequestStrategy> = listOf(
            PeriodCreateRequestStrategy,
            PeriodReadRequestStrategy,
            PeriodUpdateRequestStrategy,
            PeriodDeleteRequestStrategy,
            TaxResidencyRequestStrategy
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClass = members.associateBy { it.clazz }
    }
}