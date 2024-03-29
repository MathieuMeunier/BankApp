package com.example.banks.ui.model

data class AccountUI (
    val order: Int,
    val id: String,
    val holder: String,
    val role: Int,
    val contractNumber: String,
    val label: String,
    val productCode: String,
    val balance: Double,
    val operations: List<OperationUI>
)
