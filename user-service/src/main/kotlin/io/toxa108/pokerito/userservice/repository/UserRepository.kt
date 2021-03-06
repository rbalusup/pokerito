package io.toxa108.pokerito.userservice.repository

import com.github.jasync.sql.db.RowData
import com.github.jasync.sql.db.SuspendingConnection
import com.github.jasync.sql.db.asSuspending
import io.toxa108.pokerito.userservice.repository.db.DBConnectException
import io.toxa108.pokerito.userservice.repository.db.DatabaseProvider
import io.toxa108.pokerito.userservice.repository.entity.UserEntity
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors.toList

@Component
class UserRepository(private val databaseProvider: DatabaseProvider): Repository<UUID, UserEntity> {

    companion object {
        const val TABLE_NAME = "poker_user"
    }

    private val connection = databaseProvider.connectionPool.asSuspending

    override suspend fun saveInTx(connection: SuspendingConnection, data: UserEntity) {
        save(connection, data)
    }

    override suspend fun save(data: UserEntity) {
        save(this.connection, data)
    }

    private suspend fun save(connection: SuspendingConnection, data: UserEntity) {
        connection.sendQuery(
                "insert into $TABLE_NAME (id, email, login, password, walletId) values " +
                        "(UUID_TO_BIN('${data.id}', true), \"${data.email}\", \"${data.login}\", \"${data.password}\", UUID_TO_BIN('${data.walletId}', true))")
                .let {
                    if (it.rowsAffected == 0L) throw DBConnectException("err")
                }
    }

    override suspend fun updateInTx(connection: SuspendingConnection, data: UserEntity) {
        update(connection, data)
    }

    override suspend fun update(data: UserEntity) {
        update(this.connection, data)
    }

    private suspend fun update(connection: SuspendingConnection, data: UserEntity) {
        connection
                .sendQuery(
                "update $TABLE_NAME email = \"${data.email}\", login = \"${data.login}\", password = \"${data.password}\", walletId = UUID_TO_BIN('${data.walletId}', true) where id = UUID_TO_BIN('${data.id}', true)")
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

    override suspend fun findById(id: UUID): UserEntity? =
        connection
                .sendQuery("select BIN_TO_UUID(id, true) AS id, email, login, password, BIN_TO_UUID(walletId, true) AS walletId from $TABLE_NAME where id = UUID_TO_BIN('${id}', true)")
                .rows
                .let {
                    return if (it.isEmpty()) null
                    else map(it[0])
                }

    suspend fun findByLogin(login: String): UserEntity? =
            connection.sendQuery("select BIN_TO_UUID(id, true) AS id, email, login, password, BIN_TO_UUID(walletId, true) AS walletId from $TABLE_NAME where login = '$login'")
                    .rows
                    .let {
                        return if (it.isEmpty()) null
                        else map(it[0])
                    }

    suspend fun findByLoginOrEmail(login: String, email: String): UserEntity? =
            connection.sendQuery("select BIN_TO_UUID(id, true) AS id, email, login, password, BIN_TO_UUID(walletId, true) AS walletId from $TABLE_NAME where login = '$login' or email = '$email'")
                    .rows
                    .let {
                        return if (it.isEmpty()) null
                        else map(it[0])
                    }

    override suspend fun findAll(): List<UserEntity> {
        return connection
                .sendQuery("select BIN_TO_UUID(id, true) AS id, email, login, password, BIN_TO_UUID(walletId, true) AS walletId from $TABLE_NAME")
                .rows.stream().map { r -> map(r) }
                ?.collect(toList())?.let { it as List<UserEntity> } ?: listOf()
    }

    private fun map (rowData: RowData): UserEntity {
        return UserEntity.Builder()
                .id(UUID.fromString(rowData["id"] as String))
                .email(rowData["email"] as String)
                .login(rowData["login"] as String)
                .password(rowData["password"] as String)
                .walletId(UUID.fromString(rowData["walletId"] as String))
                .build()
    }
}