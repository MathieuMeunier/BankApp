package com.example.banks.ui.accounts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banks.ui.model.BankUI
import com.example.banks.repository.BankRepository
import com.example.banks.utils.DataState
import com.example.banks.utils.convertToDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

interface BankAccountsViewModel {
    val bankAccounts: LiveData<DataState<List<BankUI>>>
    fun getBankAccounts()
}

@HiltViewModel
class BankAccountsViewModelImpl @Inject constructor(private val bankRepository: BankRepository) : BankAccountsViewModel, ViewModel() {

    private val _bankAccounts: MutableLiveData<DataState<List<BankUI>>> = MutableLiveData()

    override val bankAccounts: LiveData<DataState<List<BankUI>>>
        get() = _bankAccounts

    override fun getBankAccounts() {
        viewModelScope.launch {
            val result = bankRepository.getAccounts().convertToDataState()
            _bankAccounts.postValue(result)
        }
    }
}