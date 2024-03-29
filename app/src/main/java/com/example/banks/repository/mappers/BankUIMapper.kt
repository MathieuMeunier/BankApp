package com.example.banks.repository.mappers

import com.example.banks.repository.model.Bank
import com.example.banks.ui.model.BankUI
import com.example.banks.utils.Mapper
import javax.inject.Inject

class BankUIMapper @Inject constructor(private val accountUIMapper: AccountUIMapper):
    Mapper<Bank, BankUI> {

    override fun mapFromEntity(entity: Bank): BankUI {
        return BankUI(
            name = entity.name,
            isMainBank = entity.isMainBank,
            accounts = accountUIMapper.mapFromEntityList(entity.accounts)
        )
    }

    override fun mapFromEntityList(entities: List<Bank>): List<BankUI> {
        return entities.map { mapFromEntity(it) }
    }
}