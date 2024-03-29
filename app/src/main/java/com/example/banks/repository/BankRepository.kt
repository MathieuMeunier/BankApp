package com.example.banks.repository

import com.example.banks.utils.ApiHandler
import com.example.banks.network.apis.BankAccountServices
import com.example.banks.repository.mappers.BankUIMapper
import com.example.banks.ui.model.AccountUI
import com.example.banks.ui.model.BankUI
import com.example.banks.utils.NetworkResult
import javax.inject.Inject

class BankRepository @Inject constructor(
    private val bankAccountServices: BankAccountServices,
    private val bankMapper: BankUIMapper
) : ApiHandler {

    suspend fun getAccounts(): NetworkResult<List<BankUI>> {
        return when(val result = bankAccountServices.getBankAccounts()) {
            is NetworkResult.Error -> result
            is NetworkResult.Exception -> result
            is NetworkResult.Success -> {
                NetworkResult.Success(result.code, bankMapper.mapFromEntityList(result.data))
            }
        }
    }

    suspend fun getAccount(accountIdentifier: String): NetworkResult<AccountUI> {
        return when(val result = bankAccountServices.getBankAccounts()) {
            is NetworkResult.Error -> result
            is NetworkResult.Exception -> result
            is NetworkResult.Success -> {
                bankMapper.mapFromEntityList(result.data).flatMap { it.accounts }.first { it.id == accountIdentifier }.let {
                    NetworkResult.Success(result.code, it)
                }
            }
        }
    }
}