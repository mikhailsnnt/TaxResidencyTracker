package mikhailsnnt.taxresiden.app.ktor.auth

import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable
import mikhailsnnt.taxresiden.app.ktor.applicationHttpClient
import mikhailsnnt.taxresident.common.TxContext


@Serializable
data class YandexInfoResponse(
    val id: String,
    val default_email: String
)

suspend fun TxContext.authenticate(yandexToken: String)=apply{
    userId = applicationHttpClient.get("https://login.yandex.ru/info"){
        parameter("oauth_token", yandexToken)
    }.body<YandexInfoResponse>().id
}