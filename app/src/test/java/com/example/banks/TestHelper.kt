package com.example.banks

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.banks.network.model.AccountResponse
import com.example.banks.network.model.BankResponse
import com.example.banks.network.model.OperationResponse
import com.example.banks.ui.model.AccountUI
import com.example.banks.ui.model.BankUI
import com.example.banks.ui.model.OperationUI
import com.example.banks.network.DataResult
import com.example.banks.network.NetworkResult
import com.example.banks.network.convertToDataState
import java.io.File
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class TestHelper {

        companion object {

            val operation1 = OperationResponse(
                "1",
                "Tenue de compte",
                "-1,99",
                "costs",
                "1644870724"
            )

            val operation2 = OperationResponse(
                "2",
                "Prélèvement Orange",
                "-45,99",
                "leisure",
                "1644870724"
            )

            val operation3 = OperationResponse(
                "3",
                "Prêt immo",
                "-1345,99",
                "costs",
                "1644179569"
            )

            val operation4 = OperationResponse(
                "4",
                "Salaire",
                "13345,99",
                "income",
                "1644611558"
            )

            val account1 = AccountResponse(
                1,
                "1",
                "Michel Berger",
                2,
                "",
                "Account 1",
                "",
                32410.32,
                listOf(operation1, operation2)
            )

            val account2 = AccountResponse(
                2,
                "2",
                "Michel Berger",
                2,
                "",
                "Account 2",
                "",
                0.92,
                listOf(operation3, operation4)
            )

            val account3 = AccountResponse(
                3,
                "3",
                "Michel Berger",
                2,
                "",
                "Account 3",
                "",
                -3401.34,
                listOf(operation1, operation2, operation3, operation4)
            )

            val bank1 = BankResponse(
                "Crédit Agricole Auvergne Rhône Alpes",
                1,
                listOf(account1, account2, account3)
            )

            val bank2 = BankResponse(
                "LCL",
                0,
                listOf(account2, account3)
            )

            val bank3 = BankResponse(
                "My Crazy Internet Bank",
                0,
                listOf(account1, account2)
            )

            val bankResponse : List<BankResponse> = listOf(bank1, bank2, bank3)

            val emptyBankResponse = listOf<BankResponse>()

            fun getJsonStringFromFile(filePath: String): String {
                return File(ASSET_BASE_PATH + filePath).readText()
            }

            private const val ASSET_BASE_PATH = "../app/src/test/java/com/example/banks/apiresponse/"
        }

    class Data {
        companion object {
            val operationUI1 = OperationUI(
                "2",
                "Prélèvement Netflix",
                "-15,99",
                "leisure",
                "1644870724"
            )

            val operationUI2 = OperationUI(
                "4",
                "CB Amazon",
                "-95,99",
                "online",
                "1644611558"
            )

            val account1 = AccountUI(
                1,
                "151515151151",
                "Corinne Martin",
                1,
                "32216549871",
                "Compte de dépôt",
                "00004",
                2031.84,
                listOf(operationUI1, operationUI2)
            )

            val operationUI3 = OperationUI(
                "2",
                "Prélèvement Netflix",
                "-15,99",
                "leisure",
                "1644784369"
            )

            val operationUI4 = OperationUI(
                "3",
                "Prélèvement Century 21",
                "-750,00",
                "housing",
                "1644179569"
            )

            val account2 = AccountUI(
                2,
                "9892736780987654",
                "M. et Mme Martin",
                2,
                "09320939231",
                "Compte joint",
                "00007",
                843.15,
                listOf(operationUI3, operationUI4)
            )

            val operationUI5 = OperationUI(
                "2",
                "Orange",
                "-15,99",
                "leisure",
                "1644438769"
            )

            val account3 = AccountUI(
                3,
                "2354657678098765",
                "Thaïs Martin",
                6,
                "29389382872",
                "Compte Mozaïc",
                "00007",
                209.39,
                listOf(operationUI5)
            )

            val bankUI = BankUI(
                "CA Languedoc",
                true,
                listOf(account1, account2, account3)
            )

            val operationUI6 = OperationUI(
                "2",
                "Tenue de compte",
                "-1,99",
                "costs",
                "1588690878"
            )

            val operationUI7 = OperationUI(
                "3",
                "Tenue de compte",
                "-1,99",
                "costs",
                "1641760369"
            )

            val account4 = AccountUI(
                1,
                "09878900000",
                "Corinne Martin",
                1,
                "32216549871",
                "Compte de dépôt",
                "00004",
                45.84,
                listOf(operationUI6, operationUI7)
            )

            val bankUI2 = BankUI(
                "Boursorama",
                false,
                listOf(account4)
            )


            val bankUIS : List<BankUI> = listOf(bankUI, bankUI2)
            val bankNetworkResult = NetworkResult.Success(200, bankUIS)
            val bankAccount = NetworkResult.Success(200, account1)

            val dataAccountStateResult: DataResult<AccountUI> = bankAccount.convertToDataState()
            val dataBankAccountsStateResultUI: DataResult<List<BankUI>> = bankNetworkResult.convertToDataState()
        }
    }

}

/* Copyright 2019 Google LLC.
   SPDX-License-Identifier: Apache-2.0 */
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            data = value
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}