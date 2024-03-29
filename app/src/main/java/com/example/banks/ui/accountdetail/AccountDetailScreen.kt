package com.example.banks.ui.accountdetail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.banks.ui.component.NavigationTopBar
import com.example.banks.ui.model.AccountUI
import com.example.banks.ui.model.OperationUI
import com.example.banks.utils.Data
import com.example.banks.utils.DataState
import com.example.banks.utils.ListDivider
import com.example.banks.utils.UiUtils

@Composable
fun AccountDetailScreen(
    navHostController: NavHostController,
    viewModel: AccountDetailViewModel,
    accountId: String? = null
) {

    val accountState = viewModel.account.observeAsState()

    LaunchedEffect(key1 = Unit) {
        accountId?.let {
            viewModel.getAccount(it)
        }
    }

    when (val state = accountState.value) {
        is DataState.Failure -> {
            // Show Error
            Log.e("AccountDetailScreen", state.errorMessage.toString())
        }
        DataState.Loading -> {
            // Show Loading
            Log.v("AccountDetailScreen", "Loading")
        }
        is DataState.Success -> {
            TopBarAccountDetail(
                navHostController,
                state.data
            )
        }
        null -> {
            // Show Error with null
            Log.v("AccountDetailScreen", "Null")
        }
    }
}

@Composable
fun TopBarAccountDetail(
    navHostController: NavHostController,
    account: AccountUI
) {
    Scaffold(
        topBar = {
            NavigationTopBar(
                title = "",
                canNavigateBack = true,
                navigateUp = {
                    navHostController.popBackStack()
                })
        },
    ) { innerPadding ->
        AccountDetail(innerPadding = innerPadding, account = account)
    }
}

@Composable
fun AccountDetail(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    account: AccountUI
    ) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = modifier.padding(bottom = 24.dp),
                color = Color.Black,
                fontSize = 24.sp,
                text = UiUtils.formatBalanceToString(account.balance)
            )
            Text(
                modifier = modifier.padding(bottom = 8.dp),
                color = Color.Black,
                text = account.label
            )
            LazyColumn(
                modifier = modifier.background(Color.White)
            ) {
                val sortedOperations = account.operations.sortedWith(
                    compareByDescending<OperationUI> { it.date }.thenBy { it.title }
                )
                items(sortedOperations.size) { operationIndex ->
                    OperationRow(
                        modifier = modifier,
                        operationUI = sortedOperations[operationIndex]
                    )
                    ListDivider()
                }
            }
        }
    }
}

@Composable
fun OperationRow(modifier: Modifier = Modifier,
                 operationUI: OperationUI) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        ) {
        Column(
            modifier = modifier.padding(start = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                fontSize = 12.sp,
                text = operationUI.title
            )
            UiUtils.getDateTime(operationUI.date)?.let {
                Text(
                    modifier = modifier.padding(start = 16.dp),
                    fontSize = 12.sp,
                    text = it,
                )
            }
        }
        Text(
            modifier = modifier.padding(end = 36.dp),
            textAlign = TextAlign.End,
            color = Color.Gray,
            fontSize = 12.sp,
            text = "${operationUI.amount} €"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview(modifier: Modifier = Modifier) {
    MaterialTheme {
        TopBarAccountDetail(
            rememberNavController(),
            Data.bankUIS.last().accounts.last()
        )
    }
}