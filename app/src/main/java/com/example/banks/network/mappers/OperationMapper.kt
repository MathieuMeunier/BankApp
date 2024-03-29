package com.example.banks.network.mappers

import com.example.banks.network.model.OperationResponse
import com.example.banks.repository.model.Operation
import com.example.banks.utils.Mapper
import javax.inject.Inject

class OperationMapper @Inject constructor():
    Mapper<OperationResponse, Operation> {

    override fun mapFromEntity(entity: OperationResponse): Operation {
        return Operation(
            id = entity.id,
            title = entity.title,
            amount = entity.amount,
            category = entity.category,
            date = entity.date,
        )
    }

    override fun mapFromEntityList(entities: List<OperationResponse>): List<Operation> {
        return entities.map { mapFromEntity(it) }
    }
}