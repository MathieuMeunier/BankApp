package com.example.banks.network.mappers

import com.example.banks.network.model.BankResponse
import com.example.banks.repository.model.Bank
import com.example.banks.utils.Mapper
import javax.inject.Inject

class BankMapper @Inject constructor(private val accountsMapper: AccountsMapper):
    Mapper<BankResponse, Bank> {

    override fun mapFromEntity(entity: BankResponse): Bank {
        return Bank(
            name = entity.name,
            isMainBank =  entity.isMainBank == 1,
            accounts = accountsMapper.mapFromEntityList(entity.accounts)
        )
    }

    override fun mapFromEntityList(entities: List<BankResponse>): List<Bank> {
        return entities.map { mapFromEntity(it) }
    }
}