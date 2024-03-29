package com.example.banks.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OperationResponse(
    @SerializedName("id")
    @Expose
    var id: String,
    @SerializedName("title")
    @Expose
    var title: String,
    @SerializedName("amount")
    @Expose
    var amount: String,
    @SerializedName("category")
    @Expose
    var category: String,
    @SerializedName("date")
    @Expose
    var date: String
)
