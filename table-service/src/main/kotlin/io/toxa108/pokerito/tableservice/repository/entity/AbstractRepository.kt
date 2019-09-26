package io.toxa108.pokerito.tableservice.repository.entity

import com.github.jasync.sql.db.RowData
import com.github.jasync.sql.db.SuspendingConnection
import com.github.jasync.sql.db.asSuspending
import io.toxa108.pokerito.tableservice.repository.Repository
import io.toxa108.pokerito.tableservice.repository.db.DatabaseProvider

abstract class AbstractRepository<UUID, T>(databaseProvider: DatabaseProvider,
                                           private val tableName: String
) : Repository<UUID, T> {

    protected val connection = databaseProvider.connectionPool.asSuspending

    override suspend fun saveInTx(connection: SuspendingConnection, data: T) {
        save(connection, data)
    }

    override suspend fun save(data: T) {
        save(this.connection, data)
    }
    protected abstract suspend fun save(connection: SuspendingConnection, data: T)

    override suspend fun updateInTx(connection: SuspendingConnection, data: T) {
        update(connection, data)
    }

    override suspend fun update(data: T) {
        update(this.connection, data)
    }

    protected abstract suspend fun update(connection: SuspendingConnection, data: T)

    override suspend fun delete(id: UUID) {
        connection.sendQuery("delete from $tableName where id = UUID_TO_BIN('${id}', true)")
    }

    override suspend fun deleteAll() {
        connection.sendQuery("delete from $tableName")
    }

    protected abstract fun map (rowData: RowData): T

    protected fun safeSqlNull(obj: Any?): String {
        return if (obj == null) "null"
        else "\"$obj\""
    }
}