package io.toxa108.pokerito.userservice.repository

interface Repository<ID, T> {
    suspend fun save(data: T): T
    suspend fun update(data: T): T
    suspend fun delete(id: ID)
    suspend fun findById(id: ID): T?
    suspend fun findAll(): List<T>
}