package com.example.banks.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AccountResponse(
    @SerializedName("order")
    @Expose
    var order: Int,
    @SerializedName("id")
    @Expose
    var id: String,
    @SerializedName("holder")
    @Expose
    var holder: String,
    @SerializedName("role")
    @Expose
    var role: Int,
    @SerializedName("contract_number")
    @Expose
    var contractNumber: String,
    @SerializedName("label")
    @Expose
    var label: String,
    @SerializedName("product_code")
    @Expose
    var productCode: String,
    @SerializedName("balance")
    @Expose
    var balance: Double,
    @SerializedName("operations")
    @Expose
    var operations: List<OperationResponse>
)
