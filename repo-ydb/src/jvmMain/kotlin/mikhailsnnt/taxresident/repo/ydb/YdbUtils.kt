package mikhailsnnt.taxresident.repo.ydb

import com.yandex.ydb.table.result.ResultSetReader


fun <T> ResultSetReader.readEntities(mapRow: ResultSetReader.()->T): List<T>{
    val res =  mutableListOf<T>()
    while (next()){
        res += mapRow()
    }
    return res
}