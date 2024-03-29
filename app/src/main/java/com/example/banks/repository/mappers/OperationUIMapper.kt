package com.example.banks.repository.mappers

import com.example.banks.repository.model.Operation
import com.example.banks.ui.model.OperationUI
import com.example.banks.utils.Mapper
import javax.inject.Inject

class OperationUIMapper @Inject constructor():
    Mapper<Operation, OperationUI> {

    override fun mapFromEntity(entity: Operation): OperationUI {
        return OperationUI(
            id = entity.id,
            title = entity.title,
            amount = entity.amount,
            category = entity.category,
            date = entity.date,
        )
    }

    override fun mapFromEntityList(entities: List<Operation>): List<OperationUI> {
        return entities.map { mapFromEntity(it) }
    }
}