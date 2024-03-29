package com.example.banks.ui.model

data class BankUI(
    val name: String,
    val isMainBank: Boolean,
    val accounts: List<AccountUI>
)
