package com.example.banks.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.banks.ui.model.AccountUI
import com.example.banks.ui.model.BankUI
import com.example.banks.ui.model.OperationUI
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UiUtils {

    companion object {
        fun formatBalanceToString(balance: Double): String {
            return String.format("%.2f €", balance)
        }

        fun getDateTime(s: String): String? {
            return try {
                val dateFormatter = SimpleDateFormat("MM/dd/yyyy", Locale.FRANCE)
                val netDate = Date(s.toLong()  * 1000)
                dateFormatter.format(netDate)
            } catch (e: Exception) {
                e.toString()
            }
        }
    }

}

@Composable
fun ListDivider(startPadding: Int = 16, endPadding: Int = 0, alpha: Float = 0.20f) {
    HorizontalDivider(
        modifier = Modifier.padding(start = startPadding.dp, end = endPadding.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha)
    )
}

@Composable
fun ListDividerPadding(padding: Int = 48, endPadding: Int = 0, alpha: Float = 0.20f) {
    HorizontalDivider(
        modifier = Modifier.padding(start = padding.dp, end = endPadding.dp),
        color = MaterialTheme.colorScheme.primary.copy(alpha = alpha)
    )

}

class Data {
    companion object {
        val operationUI1 = OperationUI(
            "1",
            "Tenue de compte",
            "-1,99",
            "costs",
            "1644870724"
        )

        val operationUI2 = OperationUI(
            "2",
            "Prélèvement Orange",
            "-45,99",
            "leisure",
            "1644870724"
        )

        val operationUI3 = OperationUI(
            "3",
            "Prêt immo",
            "-1345,99",
            "costs",
            "1644179569"
        )

        val operationUI4 = OperationUI(
            "4",
            "Salaire",
            "13345,99",
            "income",
            "1644611558"
        )

        val account1 = AccountUI(
            1,
            "1",
            "Michel Berger",
            2,
            "",
            "Account 1",
            "",
            32410.32,
            listOf(operationUI1, operationUI2)
        )

        val account2 = AccountUI(
            2,
            "2",
            "Michel Berger",
            2,
            "",
            "Account 2",
            "",
            0.92,
            listOf(operationUI3, operationUI4)
        )

        val account3 = AccountUI(
            3,
            "3",
            "Michel Berger",
            2,
            "",
            "Account 3",
            "",
            -3401.34,
            listOf(operationUI1, operationUI2, operationUI3, operationUI4)
        )

         val bankUI1 = BankUI(
            "Crédit Agricole Auvergne Rhône Alpes",
            true,
            listOf(account1, account2, account3)
        )

        val bankUI2 = BankUI(
            "LCL",
            false,
            listOf(account2, account3)
        )

        val bankUI3 = BankUI(
            "My Crazy Internet Bank",
            false,
            listOf(account1, account2)
        )

        val bankUIS : List<BankUI> = listOf(bankUI1, bankUI2, bankUI3)
    }
}