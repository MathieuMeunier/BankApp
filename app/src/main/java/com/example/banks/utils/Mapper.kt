package com.example.banks.utils

interface Mapper<Entity, Model> {
    fun mapFromEntity(entity: Entity): Model
    fun mapFromEntityList(entities: List<Entity>) : List<Model>
}