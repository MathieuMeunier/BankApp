package com.example.banks.ui.accountdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banks.repository.BankRepository
import com.example.banks.ui.model.AccountUI
import com.example.banks.utils.DataState
import com.example.banks.utils.convertToDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

interface AccountDetailViewModel {
    val account: LiveData<DataState<AccountUI>>
    fun getAccount(accountIdentifier: String)
}

@HiltViewModel
class AccountDetailViewModelImpl @Inject constructor(private val bankRepository: BankRepository) : AccountDetailViewModel, ViewModel() {

    private val _account: MutableLiveData<DataState<AccountUI>> = MutableLiveData()

    override val account: LiveData<DataState<AccountUI>>
        get() = _account

    override fun getAccount(accountIdentifier: String) {
        viewModelScope.launch {
            val result = bankRepository.getAccount(accountIdentifier).convertToDataState()
            _account.postValue(result)
        }
    }
}