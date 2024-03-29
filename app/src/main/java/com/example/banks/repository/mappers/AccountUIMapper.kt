package com.example.banks.repository.mappers

import com.example.banks.repository.model.Account
import com.example.banks.ui.model.AccountUI
import com.example.banks.utils.Mapper
import javax.inject.Inject

class AccountUIMapper @Inject constructor(private val operationUIMapper: OperationUIMapper):
    Mapper<Account, AccountUI> {

    override fun mapFromEntity(entity: Account): AccountUI {
        return AccountUI(
            order = entity.order,
            id = entity.id,
            holder = entity.holder,
            role = entity.role,
            contractNumber = entity.contractNumber,
            label = entity.label,
            productCode = entity.productCode,
            balance = entity.balance,
            operations = operationUIMapper.mapFromEntityList(entity.operations)
        )
    }

    override fun mapFromEntityList(entities: List<Account>): List<AccountUI> {
        return entities.map { mapFromEntity(it) }
    }
}