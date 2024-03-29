package com.example.banks.repository.model

data class Operation(
    val id: String,
    val title: String,
    val amount: String,
    val category: String,
    val date: String
)