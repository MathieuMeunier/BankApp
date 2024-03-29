package com.example.banks.repository.model

data class Bank(
    val name: String,
    val isMainBank: Boolean,
    val accounts: List<Account>
)
