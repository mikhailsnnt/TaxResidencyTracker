package mikhailsnnt.taxresident.lib.cor.handlers

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import mikhailsnnt.taxresident.lib.cor.*

class CorChain<T>(
    title: String,
    description: String,
    blockOn: suspend T.() -> Boolean,
    blockExcept: suspend T.(Throwable) -> Unit = { throw it },
    private val handler: suspend (T, List<ICorExec<T>>) -> Unit,
    private val execs: List<ICorExec<T>>
) : AbstractICorExec<T>(title, description, blockOn, blockExcept) {
    override suspend fun handle(context: T) {
        handler.invoke(context, execs)
    }
}

suspend fun <T> sequential(context: T, execs: List<ICorExec<T>>) {
    execs.forEach { it.exec(context) }
}

suspend fun <T> parallel(context: T, execs: List<ICorExec<T>>) = coroutineScope {
    execs.forEach {
        launch {
            it.exec(context)
        }
    }
}

@CorDslMarker
class CorChainDsl<T>(
    private val handler: suspend (T, List<ICorExec<T>>) -> Unit = ::sequential,
): CorExecDsl<T>() , ICorChainDsl<T>{
    private val workers = mutableListOf<ICorExecDsl<T>>()

    override fun add(worker: ICorExecDsl<T>){
        workers += worker
    }

    override fun build() = CorChain(
        title = title,
        description = description,
        blockOn = blockOn,
        blockExcept = blockExcept,
        handler = handler,
        execs = workers.map{it.build()}
    )

}