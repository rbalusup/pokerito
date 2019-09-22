package io.toxa108.pokerito.userservice.repository

import com.github.jasync.sql.db.RowData
import com.github.jasync.sql.db.SuspendingConnection
import com.github.jasync.sql.db.asSuspending
import io.toxa108.pokerito.userservice.repository.db.DBConnectException
import io.toxa108.pokerito.userservice.repository.db.DatabaseProvider
import io.toxa108.pokerito.userservice.repository.entity.WalletEntity
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*
import java.util.stream.Collectors.toList

@Component
class WalletRepository(private val databaseProvider: DatabaseProvider): Repository<UUID, WalletEntity> {

    companion object {
        const val TABLE_NAME = "poker_wallet"
    }

    private val connection: SuspendingConnection = databaseProvider.connectionPool.asSuspending

    override suspend fun saveInTx(connection: SuspendingConnection, data: WalletEntity) {
        save(connection, data)
    }

    override suspend fun save(data: WalletEntity) {
        save(this.connection, data)
    }

    private suspend fun save(connection: SuspendingConnection, data: WalletEntity) {
        connection.sendQuery("insert into $TABLE_NAME (id, amount) values (UUID_TO_BIN('${data.id}', true), \"${data.amount}\")")
                .let {
                    if (it.rowsAffected == 0L) throw DBConnectException("err")
                }
    }

    override suspend fun updateInTx(connection: SuspendingConnection, data: WalletEntity) {
        update(connection, data)
    }

    override suspend fun update(data: WalletEntity) {
        update(this.connection, data)
    }

    private suspend fun update(connection: SuspendingConnection, data: WalletEntity) {
        connection
                .sendQuery("update $TABLE_NAME amount = \"${data.amount}\" where id = UUID_TO_BIN('${data.id}', true)")
                .rows
                .let {
                    if (it.isEmpty()) throw DBConnectException("err")
                }
    }

    override suspend fun delete(id: UUID) {
        connection.sendQuery("delete from $TABLE_NAME where id = UUID_TO_BIN('${id}', true)")
    }

    override suspend fun deleteAll() {
        connection.sendQuery("delete from $TABLE_NAME")
    }

    override suspend fun findById(id: UUID): WalletEntity? =
        connection
                .sendQuery("select BIN_TO_UUID(id, true) AS id, amount from $TABLE_NAME where id = UUID_TO_BIN('${id}', true)")
                .rows
                .let {
                    return if (it.isEmpty()) null
                    else map(it[0])
                }

    override suspend fun findAll(): List<WalletEntity> {
        return connection
                .sendQuery("select BIN_TO_UUID(id, true) AS id, amount from $TABLE_NAME")
                .rows.stream().map { r -> map(r) }
                ?.collect(toList())?.let { it as List<WalletEntity> } ?: listOf()
    }

    private fun map (rowData: RowData): WalletEntity {
        return WalletEntity.Builder()
                .id(UUID.fromString(rowData["id"] as String))
                .amount(rowData["amount"] as BigDecimal)
                .build()
    }
}