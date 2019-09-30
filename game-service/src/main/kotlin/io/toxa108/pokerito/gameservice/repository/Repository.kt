package io.toxa108.pokerito.gameservice.repository

import com.github.jasync.sql.db.SuspendingConnection

interface Repository<ID, T> {
    suspend fun save(data: T)
    suspend fun saveInTx(connection: SuspendingConnection, data: T)
    suspend fun update(data: T)
    suspend fun updateInTx(connection: SuspendingConnection, data: T)
    suspend fun delete(id: ID)
    suspend fun deleteAll()
    suspend fun findById(id: ID): T?
    suspend fun findAll(): List<T>
}