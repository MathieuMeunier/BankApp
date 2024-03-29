package com.example.banks.id

import com.example.banks.network.apis.BankAccountApi
import com.example.banks.network.getUnsafeOkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideBaseUrl():String = "https://cdf-test-mobile-default-rtdb.europe-west1.firebasedatabase.app/"

    @Singleton
    @Provides
    fun provideRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getUnsafeOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideBankAccountApi(retrofit: Retrofit): BankAccountApi {
        return retrofit.create(BankAccountApi::class.java)
    }

}