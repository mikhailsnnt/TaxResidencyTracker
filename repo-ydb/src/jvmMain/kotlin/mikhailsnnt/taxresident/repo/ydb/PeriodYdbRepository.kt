package mikhailsnnt.taxresident.repo.ydb

import com.yandex.ydb.auth.iam.CloudAuthHelper
import com.yandex.ydb.core.auth.AuthProvider
import com.yandex.ydb.core.grpc.GrpcTransport
import com.yandex.ydb.table.SessionRetryContext
import com.yandex.ydb.table.TableClient
import com.yandex.ydb.table.rpc.grpc.GrpcTableRpc
import com.yandex.ydb.table.transaction.TxControl
import mikhailsnnt.taxresident.common.model.TxPeriod
import mikhailsnnt.taxresident.common.model.wrappers.TxUserId
import mikhailsnnt.taxresident.common.repo.period.*
import mikhailsnnt.taxresident.common.repo.period.PeriodDbResponse.Companion.toPeriodDbError
import mikhailsnnt.taxresident.common.repo.period.PeriodsDbResponse.Companion.toPeriodsDbError
import mikhailsnnt.taxresident.common.sequence.nextUUID

//FIXME SQL INJECTIONS WTF YDB SDK Params not working out of the box, some issue with \$ symbol
class PeriodYdbRepository(connectionString: String,
                          authProvider: AuthProvider = CloudAuthHelper.getAuthProviderFromEnviron()) : IPeriodRepository, AutoCloseable {

    private val transport: GrpcTransport = GrpcTransport
        .forConnectionString(connectionString)
        .withAuthProvider(authProvider)
        .build()

    private val tableClient: TableClient = TableClient.newClient(GrpcTableRpc.ownTransport(transport)).build()

    private val retryContext: SessionRetryContext = SessionRetryContext.create(tableClient).build()


    override fun close() {
        tableClient.close()
        transport.close()
    }

    override suspend fun create(periodDbRequest: PeriodDbRequest) = mergeOperation(periodDbRequest.apply { period.id = nextUUID() }, "insert")

    override suspend fun read(userId: TxUserId, searchFilter: PeriodDbSearchFilter): PeriodsDbResponse {
        var select = "SELECT * FROM $TABLE_PERIOD WHERE tx_user_id = \"${userId}\""

        searchFilter.id?.let {
            select += " AND period_id = $it"
        }

        searchFilter.maxPeriods?.let{
            select += " LIMIT $it"
        }

        println(select)
        val result = retryContext.supplyResult{
            it.executeDataQuery(select, TxControl.serializableRw())
        }.join()

        return result.ok()
            .map {
                val periods = it.getResultSet(0).readEntities {
                    TxPeriod().apply {
                        id = String(getColumn("period_id").string)
                        startDate = getColumn("start_date").date.toString()
                        endDate = getColumn("end_date").date.toString()
                    }
                }
                PeriodsDbResponse.success(periods)
            }
            .orElseGet {
                println(result.issues.map{it.code.toString() + " "+ it.message})
                result.error().get().toPeriodsDbError()
            }
    }


    override suspend fun update(periodDbRequest: PeriodDbRequest) = mergeOperation(periodDbRequest, "UPSERT")

    override suspend fun delete(periodIdRequest: PeriodIdRequest): PeriodDbResponse {
        val select = "DELETE FROM $TABLE_PERIOD WHERE period_id = \"${periodIdRequest.id}\""

        println(select)
        val result = retryContext.supplyResult{
            it.executeDataQuery(select, TxControl.serializableRw())
        }.join()

        return result.ok()
            .map {
                PeriodDbResponse.success()
            }
            .orElseGet {
                println(result.issues.map{it.code.toString() + " "+ it.message})
                result.error().get().toPeriodDbError()
            }
    }

    private fun mergeOperation(periodDbRequest: PeriodDbRequest, operation: String): PeriodDbResponse{
        //FIXME SQL INJECTIONS WTF YDB SDK Params not working out of the box, some issue with \$ symbol
        val upsertPeriodQuery = "$operation INTO $TABLE_PERIOD (period_id, tx_user_id, start_date, end_date) VALUES " +
                "(\"${periodDbRequest.period.id}\" , \"${periodDbRequest.userId}\"  , Date(\"${periodDbRequest.period.startDate}\") , Date(\"${periodDbRequest.period.endDate}\"));"

        println(upsertPeriodQuery)
        val result = retryContext.supplyResult{
            it.executeDataQuery(upsertPeriodQuery, TxControl.serializableRw().setCommitTx(true))
        }.join()

        return result.ok()
            .map {
                PeriodDbResponse.success(periodDbRequest.period)
            }
            .orElseGet {
                println(result.issues.map{it.code.toString() + " "+ it.message})
                result.error().get().toPeriodDbError()
            }
    }


    companion object{
        private const val TABLE_PERIOD = "periods"
    }
}