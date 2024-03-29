package com.example.banks.network.mappers

import com.example.banks.TestHelper
import com.example.banks.utils.Data
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class MappersTests {

    private lateinit var bankMapper: BankMapper
    private lateinit var accountsMapper: AccountsMapper
    private lateinit var operationMapper: OperationMapper

    @Before
    fun beforeEach() {
        operationMapper = OperationMapper()
        accountsMapper = AccountsMapper(operationMapper)
        bankMapper = BankMapper(accountsMapper)
    }

    @Test
    fun operationMapper_should_map_correctly() {
        val mappedOperation = operationMapper.mapFromEntity(TestHelper.operation1)
        assertEquals(mappedOperation, Data.operationUI1)

        val mappedOperations = operationMapper.mapFromEntityList(listOf(TestHelper.operation2, TestHelper.operation3, TestHelper.operation4))
        assertEquals(mappedOperations, listOf(Data.operationUI2, Data.operationUI3, Data.operationUI4))
    }

    @Test
    fun accountMapper_should_map_correctly() {
        val mappedAccount = accountsMapper.mapFromEntity(TestHelper.account1)
        assertEquals(mappedAccount, Data.account1)

        val mappedAccounts = accountsMapper.mapFromEntityList(listOf(TestHelper.account2, TestHelper.account3))
        assertEquals(mappedAccounts, listOf(Data.account2, Data.account3))
    }

    @Test
    fun bankMapper_should_map_correctly() {
        val mappedBank = bankMapper.mapFromEntity(TestHelper.bank1)
        assertEquals(mappedBank, Data.bankUI1)

        val mappedBanks = bankMapper.mapFromEntityList(TestHelper.bankResponse)
        assertEquals(mappedBanks, Data.bankUIS)
    }
}