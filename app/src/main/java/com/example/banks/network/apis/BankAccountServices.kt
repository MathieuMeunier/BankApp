package com.example.banks.network.apis

import com.example.banks.utils.ApiHandler
import com.example.banks.network.mappers.BankMapper
import com.example.banks.repository.model.Bank
import com.example.banks.utils.NetworkResult
import javax.inject.Inject

class BankAccountServices @Inject constructor(
    private val api: BankAccountApi,
    private val mapper: BankMapper) : ApiHandler {
    suspend fun getBankAccounts(): NetworkResult<List<Bank>> {
        val response = api.getBanks()
        val mappedBody = api.getBanks().body()?.let {
            mapper.mapFromEntityList(it)
        }
        return handleApi(mappedBody, response)
    }
}