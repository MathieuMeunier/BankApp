package com.example.banks.network.apis

import com.example.banks.network.model.BankResponse
import retrofit2.Response
import retrofit2.http.GET

interface BankAccountApi {
    @GET("banks.json")
    suspend fun getBanks(): Response<List<BankResponse>>
}