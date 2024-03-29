package com.example.banks.network.mappers

import com.example.banks.repository.model.Account
import com.example.banks.network.model.AccountResponse
import com.example.banks.utils.Mapper
import javax.inject.Inject

class AccountsMapper @Inject constructor(private val operationMapper: OperationMapper):
    Mapper<AccountResponse, Account> {

    override fun mapFromEntity(entity: AccountResponse): Account {
        return Account(
            order = entity.order,
            id = entity.id,
            holder = entity.holder,
            role = entity.role,
            contractNumber = entity.contractNumber,
            label = entity.label,
            productCode = entity.productCode,
            balance = entity.balance,
            operations = operationMapper.mapFromEntityList(entity.operations)
        )
    }

    override fun mapFromEntityList(entities: List<AccountResponse>): List<Account> {
        return entities.map { mapFromEntity(it) }
    }
}