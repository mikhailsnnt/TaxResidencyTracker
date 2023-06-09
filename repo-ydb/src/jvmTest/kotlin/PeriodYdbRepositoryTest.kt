import com.yandex.ydb.core.auth.NopAuthProvider
import kotlinx.coroutines.test.runTest
import mikhailsnnt.taxresident.common.model.TxPeriod
import mikhailsnnt.taxresident.common.model.wrappers.TxUserId
import mikhailsnnt.taxresident.common.repo.IDbResponse
import mikhailsnnt.taxresident.common.repo.period.IPeriodRepository
import mikhailsnnt.taxresident.common.repo.period.PeriodDbRequest
import mikhailsnnt.taxresident.common.repo.period.PeriodIdRequest
import mikhailsnnt.taxresident.repo.ydb.PeriodYdbRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName
import kotlin.random.Random
import kotlin.random.nextULong


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class PeriodYdbRepositoryTest {
    private lateinit var repo: IPeriodRepository

    @Before
    fun initRepo() {
        val ydbContainer by lazy {
            GenericContainer(DockerImageName.parse("cr.yandex/yc/yandex-docker-local-ydb:latest")).apply {
                withEnv("host", "localhost")
                withExposedPorts(2136)
                withEnv("GRPC_PORT", "2136")
                waitingFor(Wait.forListeningPort())
                withAccessToHost(true)
                start()
            }
        }
        println(ydbContainer.isHostAccessible)
        val connectionString = "grpc://${ydbContainer.host}:${ydbContainer.getMappedPort(2136)}?database=/local"
        println(connectionString)
        repo = // DEBUG
            PeriodYdbRepository(connectionString, NopAuthProvider.INSTANCE).apply {
                createTestTable()
            }

    }

    @Test
    fun a_shouldCreatePeriod() = runTest {
        repo.create(periodRequest()).assertSuccess()
    }


    @Test
    fun b_shouldReadPeriod() = runTest {
        val res = repo.read("userId").assertSuccess()
        assertNotNull(res.data)
        assertTrue(res.data!!.isNotEmpty())
    }

    @Test
    fun c_shouldUpdate() = runTest {
        val oldPeriod = repo.read("userId").assertSuccess().data!![0]
        val newPeriod = periodRequest().apply {
            period.id = oldPeriod.id
        }
        repo.update(newPeriod).assertSuccess()
    }

    @Test
    fun d_shouldDelete() = runTest {
        val periods = repo.read("userId").assertSuccess().data!!
        val periodToDelete = periods[0]
        repo.delete(PeriodIdRequest(periodToDelete.id)).assertSuccess()
        val newPeriods = repo.read("userId").assertSuccess().data
        val periodToBeDeleted = newPeriods!!.firstOrNull { it.id == periodToDelete.id }
        assertNull(periodToBeDeleted)
        assertEquals(periods.size - 1, newPeriods.size)
    }

    private fun periodRequest(userId: TxUserId = "userId") = PeriodDbRequest(
        userId,
        TxPeriod(
            randomId(),
            "2010-10-10",
            "2010-10-10"
        )
    )


    private fun <E> IDbResponse<E>.assertSuccess() = apply { assertTrue(isSuccess) }

    private fun randomId() = Random.nextULong().toString()
}