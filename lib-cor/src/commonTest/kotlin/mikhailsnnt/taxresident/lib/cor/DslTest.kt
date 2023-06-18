package mikhailsnnt.taxresident.lib.cor

import kotlinx.atomicfu.atomic
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeout
import kotlin.test.Test
import kotlin.test.assertEquals

class CorDslTest{

    @Test
    fun testDsl() = runTest{
        rootChain<TestContext> {
            worker{
                handle {
                    intVar += 1
                }
            }
        }.build().exec(context)

        assertEquals(1, context.intVar.value)
    }


    @Test
    fun testDsl2()= runTest{
        rootChain<TestContext> {
            worker{
                on{!conditionTrue}
                handle {
                    intVar += 1
                }
            }
        }.build().exec(context)

        assertEquals(0, context.intVar.value)
    }

    @Test
    fun testDsl_ParallelWorkers()= runTest{
        val chain = rootChain<TestContext> {
            worker {
                on { conditionTrue }
                handle {
                    repeat(1000) {
                        intVar += 1
                    }
                }
            }
            parallel {
                worker("incriment1") {
                    repeat(1000) {
                        intVar += 1
                    }
                    delay(100)
                }
                worker("incriment2") {
                    repeat(1000) {
                        intVar += 1
                    }
                    delay(100)
                }
            }
        }.build()

        withTimeout(150){
            chain.exec(context)
        }

        assertEquals(3000, context.intVar.value)
    }


    private val context = TestContext()
    class TestContext{
        var conditionTrue = true
        var intVar = atomic(0)
    }
}