package mikhailsnnt.taxresident.app.serverless

import mikhail.snnt.taxresident.api.v1.models.PeriodDeleteRequest
import mikhail.snnt.taxresident.api.v1.models.PeriodReadRequest
import mikhail.snnt.taxresident.api.v1.models.PeriodUpdateRequest
import mikhailsnnt.taxresident.app.serverless.model.Request
import mikhailsnnt.taxresident.app.serverless.model.Response
import yandex.cloud.sdk.functions.Context
import yandex.cloud.sdk.functions.YcFunction


@Suppress("unused")
class RoutingHandler : YcFunction<Request, Response> {

    override fun handle(event: Request, context: Context): Response {
        if (event.httpMethod != "POST") {
            return Response(400, emptyMap(), "Invalid http method: ${event.httpMethod}")
        }
        return when (event.path) {
            "period/create" -> processCall<PeriodUpdateRequest>(event, context)
            "period/read" -> processCall<PeriodReadRequest>(event, context)
            "period/update" -> processCall<PeriodUpdateRequest>(event, context)
            "period/delete" -> processCall<PeriodDeleteRequest>(event, context)
            else -> Response(400, emptyMap(), "Unknown path! Path: ${event.path}")
        }
    }
}