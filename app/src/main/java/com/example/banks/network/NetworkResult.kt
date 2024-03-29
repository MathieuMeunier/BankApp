package com.example.banks.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed class NetworkResult<out T : Any> {
    data class Success<out T : Any>(val code: Int, val data: T): NetworkResult<T>()
    data class Error(val code: Int, val errorMessage: String?): NetworkResult<Nothing>()
    data class Exception(val e: Throwable) : NetworkResult<Nothing>()
}

sealed class DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Error(val errorMessage: String?) : DataResult<Nothing>()
    data object Loading : DataResult<Nothing>()
}

fun <T : Any> NetworkResult<T>.convertToDataState(): DataResult<T> {
    return when (this) {
        is NetworkResult.Success -> DataResult.Success(this.data)
        is NetworkResult.Error -> DataResult.Error(this.errorMessage)
        is NetworkResult.Exception -> DataResult.Error(this.e.message)
    }
}

fun <T> Flow<T>.asResult() : Flow<DataResult<T>> {
    return this
        .map<T, DataResult<T>> { DataResult.Success(it) }
        .onStart { emit(DataResult.Loading) }
        .catch { emit(DataResult.Error(it.message)) }
}