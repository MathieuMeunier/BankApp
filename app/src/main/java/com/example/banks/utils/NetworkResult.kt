package com.example.banks.utils

sealed class NetworkResult<out T : Any> {
    data class Success<out T : Any>(val code: Int, val data: T): NetworkResult<T>()
    data class Error(val code: Int, val errorMessage: String?): NetworkResult<Nothing>()
    data class Exception(val e: Throwable) : NetworkResult<Nothing>()
}

sealed class DataState<out R> {
    data class Success<out T : Any>(val data: T) : DataState<T>()
    data class Failure(val errorMessage: String?) : DataState<Nothing>()
    data object Loading : DataState<Nothing>()
}

fun <T : Any> NetworkResult<T>.convertToDataState(): DataState<T> {
    return when (this) {
        is NetworkResult.Success -> DataState.Success(this.data)
        is NetworkResult.Error -> DataState.Failure(this.errorMessage)
        is NetworkResult.Exception -> DataState.Failure(this.e.message)
    }
}