package io.toxa108.pokerito.tableservice.repository

import com.github.jasync.sql.db.RowData
import com.github.jasync.sql.db.SuspendingConnection
import io.toxa108.pokerito.tableservice.repository.db.DBConnectException
import io.toxa108.pokerito.tableservice.repository.db.DatabaseProvider
import io.toxa108.pokerito.tableservice.repository.entity.AbstractRepository
import io.toxa108.pokerito.tableservice.repository.entity.TableEntity
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Collectors.toList

@Component
class TableRepository(databaseProvider: DatabaseProvider)
    : AbstractRepository<UUID, TableEntity>(databaseProvider, TABLE_NAME) {

    companion object {
        const val TABLE_NAME = "poker_table"
    }

    override suspend fun save(connection: SuspendingConnection, data: TableEntity) {
        connection.sendQuery(
                "insert into $TABLE_NAME (id, gameId, createTime, closeTime, players) values " +
                        "(UUID_TO_BIN('${data.id}', true), UUID_TO_BIN('${data.gameId}', true), \"${data.createTime}\", \"${data.closeTime}\", \"${data.players}\")")
                .let {
                    if (it.rowsAffected == 0L) throw DBConnectException("err")
                }
    }

    override suspend fun update(connection: SuspendingConnection, data: TableEntity) {
        connection
                .sendQuery(
                "update $TABLE_NAME closeTime = \"${data.closeTime}\", players = \"${data.players}\" where id = UUID_TO_BIN('${data.id}', true)")
                .rows
                .let {
                    if (it.isEmpty()) throw DBConnectException("err")
                }
    }

    override suspend fun findById(id: UUID): TableEntity? =
        connection
                .sendQuery("select BIN_TO_UUID(id, true) AS id, email, login, password, BIN_TO_UUID(walletId, true) AS walletId from $TABLE_NAME where id = UUID_TO_BIN('${id}', true)")
                .rows
                .let {
                    return if (it.isEmpty()) null
                    else map(it[0])
                }

    override suspend fun findAll(): List<TableEntity> {
        return connection
                .sendQuery("select BIN_TO_UUID(id, true) AS id, email, login, password, BIN_TO_UUID(walletId, true) AS walletId from $TABLE_NAME")
                .rows.stream().map { r -> map(r) }
                ?.collect(toList())?.let { it as List<TableEntity> } ?: listOf()
    }

    override fun map (rowData: RowData): TableEntity {
        return TableEntity.Builder()
                .id(UUID.fromString(rowData["id"] as String))
                .gameId(UUID.fromString(rowData["gameId"] as String))
                .createTime(rowData["createTime"] as LocalDateTime)
                .closeTime(if (rowData["closeTime"] != null) rowData["closeTime"] as LocalDateTime else null)
                .players(rowData["players"] as Short)
                .build()
    }
}