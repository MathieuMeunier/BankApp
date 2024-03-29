package com.example.banks.network.apis

import com.example.banks.TestHelper
import com.example.banks.network.mappers.AccountsMapper
import com.example.banks.network.mappers.BankMapper
import com.example.banks.network.mappers.OperationMapper
import com.google.gson.GsonBuilder
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BankUIAccountServicesTests {

    private lateinit var server: MockWebServer
    private lateinit var api: BankAccountApi
    private lateinit var service: BankAccountServices

    @Before
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(BankAccountApi::class.java)

        service = BankAccountServices(api)
    }

    @After
    fun after() {
        server.shutdown()
    }

    @Test
    fun getBankAccounts_from_service_should_return_data() = runTest {
        val responseObject = TestHelper.bankResponse
        val gson = GsonBuilder().create()
        val json = gson.toJson(responseObject)!!
        val result = MockResponse()
        result.setBody(json)
        server.enqueue(result)

        val data = service.getBankAccounts().body()
        server.takeRequest()

        TestCase.assertEquals(data, responseObject)
    }
}