package com.example.banks.network.apis

import com.example.banks.TestHelper
import com.example.banks.TestHelper.Companion.getJsonStringFromFile
import com.google.gson.GsonBuilder
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BankUIAccountApiTests {

    private lateinit var server: MockWebServer
    private lateinit var api: BankAccountApi

    @Before
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(BankAccountApi::class.java)
    }

    @After
    fun after() {
        server.shutdown()
    }

    @Test
    fun getBankAccount_should_return_success() = runTest {
        val responseObject = TestHelper.bankResponse
        val gson = GsonBuilder().create()
        val json = gson.toJson(responseObject)!!
        val result = MockResponse()
        result.setBody(json)
        server.enqueue(result)

        val data = api.getBanks().body()
        server.takeRequest()

        assertEquals(data, responseObject)
    }

    @Test
    fun getBankAccount_with_error_code_response_should_return_error() = runTest {
        val result = MockResponse()
        result.setResponseCode(404)
        server.enqueue(result)

        val data = api.getBanks()
        server.takeRequest()

        assertEquals(data.isSuccessful, false)
        assertNotNull(data.errorBody())
    }

    @Test
    fun getBankAccount_with_empty_response_should_return_error() = runTest {
        val json = getJsonStringFromFile("getBankAccount_empty_response.json")
        val result = MockResponse()

        result.setBody(json)
        server.enqueue(result)

        try {
            api.getBanks()
            server.takeRequest()
        } catch (exception: Exception) {
            assertNotNull(exception)
        }
    }

    @Test
    fun getBankAccount_with_corrupt_response_should_return_error() = runTest {
        val json = getJsonStringFromFile("getBankAccount_corrupt_response.json")
        val result = MockResponse()

        result.setBody(json)
        server.enqueue(result)

        try {
            api.getBanks()
            server.takeRequest()
        } catch (exception: Exception) {
            assertNotNull(exception)
        }
    }

}