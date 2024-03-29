package com.example.banks.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.banks.TestHelper
import com.example.banks.getOrAwaitValue
import com.example.banks.network.apis.BankAccountApi
import com.example.banks.network.apis.BankAccountServices
import com.example.banks.network.mappers.AccountsMapper
import com.example.banks.network.mappers.BankMapper
import com.example.banks.network.mappers.OperationMapper
import com.example.banks.repository.BankRepository
import com.example.banks.ui.accounts.BankAccountsViewModelImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BankUIAccountViewModelTests {

    private lateinit var server: MockWebServer
    private lateinit var api: BankAccountApi
    private lateinit var service: BankAccountServices
    private lateinit var repository: BankRepository
    private lateinit var bankMapper: BankMapper
    private lateinit var accountsMapper: AccountsMapper
    private lateinit var operationMapper: OperationMapper
    private lateinit var accountsViewModel: BankAccountsViewModelImpl

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(BankAccountApi::class.java)

        service = BankAccountServices(api)
        operationMapper = OperationMapper()
        accountsMapper = AccountsMapper(operationMapper)
        bankMapper = BankMapper(accountsMapper)
        repository = BankRepository(service, bankMapper)
        accountsViewModel = BankAccountsViewModelImpl(repository)

        Dispatchers.setMain(mainThreadSurrogate)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun after() {
        server.shutdown()
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAccount_should_return_data() = runTest(UnconfinedTestDispatcher()) {
        val json = TestHelper.getJsonStringFromFile("getBankAccount_success_response.json")
        val result = MockResponse()
        result.setBody(json)
        server.enqueue(result)

        launch(Dispatchers.Main) { accountsViewModel.bankUIAccounts() }

        assertEquals(accountsViewModel.bankAccounts.getOrAwaitValue(), TestHelper.Data.dataBankAccountsStateResultUI)
    }
}