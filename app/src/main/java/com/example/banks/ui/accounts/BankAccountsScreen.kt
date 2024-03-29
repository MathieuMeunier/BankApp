package com.example.banks.ui.accounts

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.KeyboardArrowRight
import androidx.compose.material.icons.sharp.KeyboardArrowDown
import androidx.compose.material.icons.sharp.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.banks.ui.component.NavigationTopBar
import com.example.banks.ui.model.AccountUI
import com.example.banks.ui.model.BankUI
import com.example.banks.ui.navigation.NavigationItem
import com.example.banks.utils.DataState
import com.example.banks.utils.ListDivider
import com.example.banks.utils.ListDividerPadding
import com.example.banks.utils.UiUtils


@Composable
fun BankAccountsScreen(
    navHostController: NavHostController = rememberNavController(),
    viewModel: BankAccountsViewModel
) {
    Scaffold(
        topBar = {
            NavigationTopBar(
                modifier = Modifier,
                title = "Mes Comptes",
                canNavigateBack = false)
        }
    ) { innerPadding ->

        val dataState = viewModel.bankAccounts.observeAsState()

        LaunchedEffect(key1 = Unit) {
            viewModel.getBankAccounts()
        }

        when (val state = dataState.value) {
            is DataState.Failure -> {
                // Show Error
                Log.e("BankAccountsScreen", state.errorMessage.toString())
            }
            DataState.Loading -> {
                // Show Loading
                Log.v("BankAccountsScreen", "Loading")
            }
            is DataState.Success -> {
                val mainBanks = state.data.filter { it.isMainBank }.sortedBy { it.name.lowercase() }
                val otherBanks = state.data.filter { !it.isMainBank }.sortedBy { it.name.lowercase() }
                val categories = mutableListOf<BankCategory>()

                if (mainBanks.isNotEmpty()) {
                    categories.add(BankCategory("Credit Agricole", mainBanks))
                }

                if (otherBanks.isNotEmpty()) {
                    categories.add(BankCategory("Autres Banques", otherBanks))
                }

                BankMainView(innerPadding, navHostController, Modifier, categories)
            }
            null -> {
                // Show Error with null
                Log.v("BankAccountsScreen", "Null")
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BankMainView(innerPadding: PaddingValues,
                 navHostController: NavHostController,
                 modifier: Modifier = Modifier,
                 categories: List<BankCategory>) {

    Surface(
        modifier = modifier,
        color = Color.White
    ) {
        LazyColumn(modifier = modifier.padding(innerPadding)) {
            categories.forEach { category ->
                stickyHeader {
                    BankHeader(modifier, category.name)
                }
                items(category.bankUIS.size) { bankIndex ->
                    BankRow(modifier = modifier, navHostController, category.bankUIS[bankIndex], isLast = bankIndex == category.bankUIS.lastIndex)
                }
            }
        }
    }
}

data class BankCategory(
    val name: String,
    val bankUIS: List<BankUI>
)

@Composable
fun BankRow(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    bankUI: BankUI,
    isLast: Boolean = false
) {
    val expanded = remember { mutableStateOf(false) }
    val balance = bankUI.accounts.map { it.balance }.sumOf { it }
    Surface(
        color = Color.White,
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 8.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = modifier.weight(0.6f),
                    text = bankUI.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    modifier= modifier.weight(0.3f),
                    textAlign = TextAlign.End,
                    color = Color.Gray,
                    text = UiUtils.formatBalanceToString(balance)
                )
                IconButton(
                    modifier= modifier.weight(0.1f),
                    onClick = { expanded.value = !expanded.value }
                ) {
                    Icon(
                        if (expanded.value) Icons.Sharp.KeyboardArrowUp else Icons.Sharp.KeyboardArrowDown,
                        contentDescription = "Accounts Details",
                        tint = Color.Gray)
                }
            }
            // Adding divider if not last item in category
            if (!isLast || expanded.value) ListDivider()
            if (expanded.value) {
                AccountListView(
                    modifier = Modifier,
                    navHostController = navHostController,
                    accounts = bankUI.accounts
                )
            }
        }
    }
}

@Composable
fun BankHeader(
    modifier: Modifier = Modifier,
    headerText: String
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        text = headerText,
        fontSize = 16.sp,
        color = Color.Gray
    )
}

@Composable
fun AccountListView(
    modifier: Modifier,
    navHostController: NavHostController,
    accounts: List<AccountUI>
) {
    Column(modifier = Modifier) {
        accounts.sortedBy { it.label }.forEach { account ->
            AccountRow(
                modifier = modifier,
                navHostController = navHostController,
                account
            )
            ListDividerPadding()
        }
    }

}

@Composable
fun AccountRow(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    account: AccountUI
) {
    Surface(
        color = Color.White,
        modifier = modifier.padding(start = 48.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier= modifier.weight(0.5f),
                text = account.label,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
            Text(
                modifier= modifier.weight(0.4f),
                textAlign = TextAlign.End,
                color = Color.Gray,
                text = String.format("%.2f €", account.balance)
            )
            IconButton(
                modifier= modifier.weight(0.1f),
                onClick = {
                    navHostController.navigate(NavigationItem.AccountDetail.withArgs(account.id))
                }
            ) {
                Icon(
                    Icons.AutoMirrored.Sharp.KeyboardArrowRight,
                    contentDescription = "Account Operations",
                    tint = Color.Gray)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview(modifier: Modifier = Modifier) {
    MaterialTheme {
        BankAccountsScreen(viewModel = viewModel())
    }
}
