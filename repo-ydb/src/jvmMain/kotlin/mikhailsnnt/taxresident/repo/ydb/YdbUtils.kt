package mikhailsnnt.taxresident.repo.ydb

import com.yandex.ydb.table.result.ResultSetReader


fun <T> ResultSetReader.readEntities(readEntity: ResultSetReader.()->T): List<T>{
    val res =  mutableListOf<T>()
    while (next()){
        res += readEntity()
    }
    return res
}