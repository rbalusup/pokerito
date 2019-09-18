package io.toxa108.pokerito.logicservice.repository

interface MemoryRepository<ID, T> {
    suspend fun save(data: T)
    suspend fun byId(id: ID): T?
    suspend fun all(): List<T>
}