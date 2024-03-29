package com.example.banks.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.banks.ui.accountdetail.AccountDetailScreen
import com.example.banks.ui.accountdetail.AccountDetailViewModel
import com.example.banks.ui.accountdetail.AccountDetailViewModelImpl
import com.example.banks.ui.accounts.BankAccountsScreen
import com.example.banks.ui.accounts.BankAccountsViewModel
import com.example.banks.ui.accounts.BankAccountsViewModelImpl
import com.example.banks.ui.navigation.NavigationArguments.ACCOUNT_ARG

sealed class NavigationItem(val route: String) {
    data object Home : NavigationItem(Screen.HOME.name)
    data object AccountDetail : NavigationItem(Screen.ACCOUNT_DETAIL.name)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

data object NavigationArguments {
     const val ACCOUNT_ARG = "accountId"
}

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavigationItem.Home.route,
    bankAccountsViewModel: BankAccountsViewModel = viewModel<BankAccountsViewModelImpl>(),
    accountDetailViewModel: AccountDetailViewModel = viewModel<AccountDetailViewModelImpl>()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Home.route) {
            BankAccountsScreen(
                navHostController = navController,
                viewModel = bankAccountsViewModel
            )
        }
        composable(
            route = NavigationItem.AccountDetail.route + "/{$ACCOUNT_ARG}",
            arguments = listOf(navArgument(ACCOUNT_ARG) {
                type = NavType.StringType
                nullable = true
            })
        ) { backStack ->
            AccountDetailScreen(
                navHostController = navController,
                viewModel = accountDetailViewModel,
                accountId = backStack.arguments?.getString(ACCOUNT_ARG)
            )
        }
    }
}