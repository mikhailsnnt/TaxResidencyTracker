package mikhailsnnt.taxresident.lib.cor.handlers

import mikhailsnnt.taxresident.lib.cor.CorDslMarker
import mikhailsnnt.taxresident.lib.cor.ICorWorkerDsl

class CorWorker<T>(
    title: String,
    description: String,
    blockOn: suspend T.() -> Boolean,
    blockExcept: suspend T.(Throwable) -> Unit = { throw it },
    private val blockHandle: suspend T.() -> Unit
) : AbstractICorExec<T>(title, description, blockOn, blockExcept) {
    override suspend fun handle(context: T) = context.blockHandle()
}

@CorDslMarker
class CorWorkerDsl<T> : CorExecDsl<T>(), ICorWorkerDsl<T> {
    private var blockHandle: suspend T.() -> Unit = {}
    override fun handle(function: suspend T.() -> Unit) {
        blockHandle = function
    }

    override fun build() = CorWorker(title, description, blockOn, blockExcept, blockHandle)
}