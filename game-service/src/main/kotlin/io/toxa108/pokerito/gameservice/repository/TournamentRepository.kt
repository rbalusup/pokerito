package io.toxa108.pokerito.gameservice.repository

import com.github.jasync.sql.db.RowData
import com.github.jasync.sql.db.SuspendingConnection
import io.toxa108.pokerito.gameservice.repository.db.DBConnectException
import io.toxa108.pokerito.gameservice.repository.db.DatabaseProvider
import io.toxa108.pokerito.gameservice.repository.entity.TournamentEntity
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class TournamentRepository constructor(databaseProvider: DatabaseProvider)
    : AbstractRepository<UUID, TournamentEntity>(databaseProvider, TABLE_NAME) {

    companion object {
        const val TABLE_NAME = "poker_tournament"
    }

    override suspend fun save(connection: SuspendingConnection, data: TournamentEntity) {
        connection.sendQuery(
                "insert into $TABLE_NAME (id, type, createTime, closeTime) values " +
                        "(UUID_TO_BIN('${data.id}', true),  \"${data.type.ordinal}\", \"${data.createTime}\", ${safeSqlNull(data.closeTime)})")
                .let {
                    if (it.rowsAffected == 0L) throw DBConnectException("err")
                }
    }

    override suspend fun update(connection: SuspendingConnection, data: TournamentEntity) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: UUID): TournamentEntity? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findAll(): List<TournamentEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    suspend fun countByCashType(): Int {
        return connection
                .sendQuery("select count(*) from $TABLE_NAME where type = ")
                .rows
                .count()
    }

    override fun map(rowData: RowData): TournamentEntity {
        return TournamentEntity.Builder()
                .id(UUID.fromString(rowData["id"] as String))
                .type(TournamentEntity.Type.values()[rowData["type"] as Int])
                .createTime(rowData["createTime"] as LocalDateTime)
                .closeTime(if (rowData["closeTime"] != null) rowData["closeTime"] as LocalDateTime else null)
                .build()
    }

}