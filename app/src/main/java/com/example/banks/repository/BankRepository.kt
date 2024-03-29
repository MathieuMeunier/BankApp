package com.example.banks.repository

import com.example.banks.network.DataResult
import com.example.banks.utils.ApiHandler
import com.example.banks.network.apis.BankAccountServices
import com.example.banks.repository.mappers.BankUIMapper
import com.example.banks.ui.model.AccountUI
import com.example.banks.ui.model.BankUI
import com.example.banks.network.NetworkResult
import com.example.banks.network.asResult
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BankRepository @Inject constructor(
    private val bankAccountServices: BankAccountServices,
    private val bankMapper: BankUIMapper
) : ApiHandler {

    suspend fun getAccounts(): Flow<DataResult<List<BankUI>>> {
        return flow<List<BankUI>> {
            when(val result = bankAccountServices.getBankAccounts()) {
                is NetworkResult.Error -> result
                is NetworkResult.Exception -> result
                is NetworkResult.Success -> {
                    bankMapper.mapFromEntityList(result.data).let {
                        NetworkResult.Success(result.code, it)
                    }
                }
            }
        }.asResult()
    }

    suspend fun getAccount(accountIdentifier: String): Flow<DataResult<AccountUI>> {
        return flow<AccountUI> {
            when(val result = bankAccountServices.getBankAccounts()) {
                is NetworkResult.Error -> result
                is NetworkResult.Exception -> result
                is NetworkResult.Success -> {
                    bankMapper.mapFromEntityList(result.data).flatMap { it.accounts }.first { it.id == accountIdentifier }.let {
                        NetworkResult.Success(result.code, it)
                    }
                }
            }
        }.asResult()
    }
}