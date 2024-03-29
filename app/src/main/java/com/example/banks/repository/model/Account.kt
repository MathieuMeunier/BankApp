package com.example.banks.repository.model

data class Account(
    val order: Int,
    val id: String,
    val holder: String,
    val role: Int,
    val contractNumber: String,
    val label: String,
    val productCode: String,
    val balance: Double,
    val operations: List<Operation>
)
