package mikhailsnnt.taxresident.common.repo.period

import mikhailsnnt.taxresident.common.model.wrappers.TxUserId

interface IPeriodRepository {
    suspend fun create(periodDbRequest: PeriodDbRequest): PeriodDbResponse
    suspend fun read(userId: TxUserId, searchFilter: PeriodDbSearchFilter = PeriodDbSearchFilter.NONE): PeriodsDbResponse
    suspend fun update(periodDbRequest: PeriodDbRequest): PeriodDbResponse
    suspend fun delete(periodIdRequest: PeriodIdRequest): PeriodDbResponse

    companion object {
        @Suppress("unused")
        val NONE = object : IPeriodRepository {
            override suspend fun create(periodDbRequest: PeriodDbRequest): PeriodDbResponse = throw NotImplementedError()
            override suspend fun read(userId: TxUserId, searchFilter: PeriodDbSearchFilter) = throw NotImplementedError()
            override suspend fun update(periodDbRequest: PeriodDbRequest): PeriodDbResponse = throw NotImplementedError()
            override suspend fun delete(periodIdRequest: PeriodIdRequest): PeriodDbResponse = throw NotImplementedError()
        }
    }
}