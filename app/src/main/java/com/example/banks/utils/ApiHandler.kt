package com.example.banks.utils

import com.example.banks.network.NetworkResult
import retrofit2.HttpException
import retrofit2.Response

interface ApiHandler {
    suspend fun <T : Any> handleApi(execute: suspend () -> Response<T>): NetworkResult<T> {
        return try {
            val response = execute()

            if (response.isSuccessful) {
                NetworkResult.Success(response.code(), response.body()!!)
            } else {
                NetworkResult.Error(response.code(), response.errorBody()?.string())
            }
        } catch (e: HttpException) {
            NetworkResult.Error(e.code(), e.message())
        } catch (e: Throwable) {
            NetworkResult.Exception(e)
        }
    }

    suspend fun <T : Any, M : Any> handleApi(body: T?, response: Response<M>): NetworkResult<T> {
        return try {
            if (response.isSuccessful && body != null) {
                NetworkResult.Success(response.code(), body)
            } else {
                NetworkResult.Error(response.code(), response.errorBody()?.string())
            }
        } catch (e: HttpException) {
            NetworkResult.Error(e.code(), e.message())
        } catch (e: Throwable) {
            NetworkResult.Exception(e)
        }
    }
}