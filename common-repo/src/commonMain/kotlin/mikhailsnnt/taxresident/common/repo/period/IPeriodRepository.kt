package mikhailsnnt.taxresident.common.repo.period

import mikhailsnnt.taxresident.common.model.wrappers.TxUserId

interface IPeriodRepository {
    suspend fun create(periodRequest: PeriodRequest): PeriodDbResponse
    suspend fun read(userId: TxUserId): PeriodsDbResponse
    suspend fun update(periodRequest: PeriodRequest): PeriodDbResponse
    suspend fun delete(periodIdRequest: PeriodIdRequest): PeriodDbResponse

    companion object {
        @Suppress("unused")
        val NONE = object : IPeriodRepository {
            override suspend fun create(periodRequest: PeriodRequest): PeriodDbResponse = throw NotImplementedError()
            override suspend fun read(userId: TxUserId): PeriodsDbResponse = throw NotImplementedError()
            override suspend fun update(periodRequest: PeriodRequest): PeriodDbResponse = throw NotImplementedError()
            override suspend fun delete(periodIdRequest: PeriodIdRequest): PeriodDbResponse = throw NotImplementedError()
        }
    }
}