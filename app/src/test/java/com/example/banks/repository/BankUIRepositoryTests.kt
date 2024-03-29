package com.example.banks.repository

import com.example.banks.TestHelper
import com.example.banks.network.apis.BankAccountApi
import com.example.banks.network.apis.BankAccountServices
import com.example.banks.network.mappers.AccountsMapper
import com.example.banks.network.mappers.BankMapper
import com.example.banks.network.mappers.OperationMapper
import com.google.gson.GsonBuilder
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BankUIRepositoryTests {

    private lateinit var server: MockWebServer
    private lateinit var api: BankAccountApi
    private lateinit var service: BankAccountServices
    private lateinit var repository: BankRepository
    private lateinit var bankMapper: BankMapper
    private lateinit var accountsMapper: AccountsMapper
    private lateinit var operationMapper: OperationMapper

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
    }

    @Test
    fun getAccounts_should_return_network_data_result() = runTest {
        val json = TestHelper.getJsonStringFromFile("getBankAccount_success_response.json")
        val result = MockResponse()

        result.setBody(json)
        server.enqueue(result)
        val data = repository.getAccounts()

        assertEquals(data, TestHelper.Data.bankNetworkResult)
    }
}