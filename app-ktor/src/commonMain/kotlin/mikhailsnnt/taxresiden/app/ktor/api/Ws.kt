package mikhailsnnt.taxresiden.app.ktor.api

import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import mikhail.snnt.taxresident.api.multi.v2.apiV2Mapper
import mikhail.snnt.taxresident.api.v1.models.IRequest
import mikhailsnnt.taxresiden.app.ktor.TxProcessor
import mikhailsnnt.taxresident.common.TxContext
import mikhailsnnt.taxresident.common.mappers.fromTransport
import mikhailsnnt.taxresident.common.mappers.toTransport


suspend fun WebSocketSession.txHandler(
    processor: TxProcessor,
    sessions: MutableSet<WsSession>
) {
    val wsSession = WsSession(this)

    sessions += wsSession

    incoming.receiveAsFlow()
        .mapNotNull { it as? Frame.Text }
        .map {
            try {
                val jsonRaw = it.readText()

                val req = apiV2Mapper.decodeFromString<IRequest>(jsonRaw)

                val context = TxContext().fromTransport(req)

                processor.process(context)

                val response = apiV2Mapper.encodeToString(context.toTransport())

                outgoing.send(Frame.Text(response))
            } catch (e: ClosedReceiveChannelException) {
                sessions -= wsSession
            }
        }.collect()
}


data class WsSession(
    val session: WebSocketSession
)