package mikhailsnnt.taxresident.common.repo.period

import mikhailsnnt.taxresident.common.model.wrappers.TxUserId

interface IPeriodRepository {
    fun create(periodRequest: PeriodRequest): PeriodDbResponse
    fun read(userId: TxUserId): PeriodsDbResponse
    fun update(periodRequest: PeriodRequest): PeriodDbResponse
    fun delete(periodIdRequest: PeriodIdRequest): PeriodDbResponse

    companion object {
        @Suppress("unused")
        val NONE = object : IPeriodRepository {
            override fun create(periodRequest: PeriodRequest): PeriodDbResponse = throw NotImplementedError()
            override fun read(userId: TxUserId): PeriodsDbResponse = throw NotImplementedError()
            override fun update(periodRequest: PeriodRequest): PeriodDbResponse = throw NotImplementedError()
            override fun delete(periodIdRequest: PeriodIdRequest): PeriodDbResponse = throw NotImplementedError()
        }
    }
}