package com.example.banks.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BankListResponse(
    @SerializedName("banks")
    @Expose
    val banks: List<BankResponse>
)