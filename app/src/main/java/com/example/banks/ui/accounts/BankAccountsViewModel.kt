package com.example.banks.ui.accounts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banks.ui.model.BankUI
import com.example.banks.repository.BankRepository
import com.example.banks.network.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface BankAccountsViewModel {
    val bankAccounts: StateFlow<DataResult<List<BankUI>>>
    fun getBankAccounts()
}

@HiltViewModel
class BankAccountsViewModelImpl @Inject constructor(private val bankRepository: BankRepository) : BankAccountsViewModel, ViewModel() {

    private val _bankAccounts: MutableStateFlow<DataResult<List<BankUI>>> = MutableStateFlow(DataResult.Success(listOf()))

    override val bankAccounts = _bankAccounts.asStateFlow()

    override fun getBankAccounts() {
        viewModelScope.launch {
            bankRepository.getAccounts().collect { result ->
                _bankAccounts.value = result
            }
        }
    }
}