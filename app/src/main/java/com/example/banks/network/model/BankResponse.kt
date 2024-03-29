package com.example.banks.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BankResponse(
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("isCA")
    @Expose
    val isMainBank: Int,
    @SerializedName("accounts")
    @Expose
    val accounts: List<AccountResponse>
)
