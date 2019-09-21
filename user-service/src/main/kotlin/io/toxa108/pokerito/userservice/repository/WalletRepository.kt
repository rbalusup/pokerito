package io.toxa108.pokerito.userservice.repository

import com.github.jasync.sql.db.RowData
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

    override suspend fun save(data: WalletEntity) {
        databaseProvider
                .connectionPool
                .asSuspending.sendQuery(
                "insert into $TABLE_NAME (id, amount) values (UUID_TO_BIN('${data.id}', true), \"${data.amount}\")")
                .let {
                    if (it.rowsAffected == 0L) throw DBConnectException("err")
                }
    }

    override suspend fun update(data: WalletEntity) {
        databaseProvider
                .connectionPool
                .asSuspending
                .sendQuery(
                "update $TABLE_NAME amount = \"${data.amount}\" where id = UUID_TO_BIN('${data.id}', true)")
                .rows
                .let {
                    if (it.isEmpty()) throw DBConnectException("err")
                }
    }

    override suspend fun delete(id: UUID) {
        databaseProvider
                .connectionPool
                .asSuspending
                .sendQuery("delete from $TABLE_NAME where id = UUID_TO_BIN('${id}', true)")
    }

    override suspend fun deleteAll() {
        databaseProvider
                .connectionPool
                .asSuspending
                .sendQuery("delete from $TABLE_NAME")
    }

    override suspend fun findById(id: UUID): WalletEntity? =
        databaseProvider
                .connectionPool
                .asSuspending
                .sendQuery("select BIN_TO_UUID(id, true) AS id, amount from $TABLE_NAME where id = UUID_TO_BIN('${id}', true)")
                .rows
                .let {
                    return if (it.isEmpty()) null
                    else map(it[0])
                }

    override suspend fun findAll(): List<WalletEntity> {
        return databaseProvider
                .connectionPool
                .asSuspending
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