package com.example.banks.id

import com.example.banks.network.apis.BankAccountServices
import com.example.banks.repository.BankRepository
import com.example.banks.repository.mappers.BankUIMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideBankRepository(
        bankAccountServices: BankAccountServices,
        bankMapper: BankUIMapper
    ): BankRepository {
        return BankRepository(bankAccountServices, bankMapper)
    }

}