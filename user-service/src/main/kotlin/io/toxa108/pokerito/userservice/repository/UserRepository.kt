package io.toxa108.pokerito.userservice.repository

import com.github.jasync.sql.db.RowData
import com.github.jasync.sql.db.asSuspending
import io.toxa108.pokerito.userservice.repository.entity.UserEntity
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors.toList

@Component
class UserRepository(private val databaseProvider: DatabaseProvider): Repository<UUID, UserEntity> {

    companion object {
        const val TABLE_NAME = "poker_user"
    }

    override suspend fun save(data: UserEntity) {
        databaseProvider
                .connectionPool
                .asSuspending.sendQuery(
                "insert into $TABLE_NAME (id, email, login, password, walletId) values " +
                        "(UUID_TO_BIN('${data.id}', true), \"${data.email}\", \"${data.login}\", \"${data.password}\", UUID_TO_BIN('${data.walletId}', true))")
                .let {
                    if (it.rowsAffected == 0L) throw DBConnectException("err")
                }
    }

    override suspend fun update(data: UserEntity) {
        databaseProvider
                .connectionPool
                .asSuspending
                .sendQuery(
                "update $TABLE_NAME email = \"${data.email}\", login = \"${data.login}\", password = \"${data.password}\", walletId = UUID_TO_BIN('${data.walletId}', true)")
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

    override suspend fun findById(id: UUID): UserEntity? =
        databaseProvider
                .connectionPool
                .asSuspending
                .sendQuery("select BIN_TO_UUID(id, true) AS id, email, login, password, BIN_TO_UUID(walletId, true) AS walletId from $TABLE_NAME where id = UUID_TO_BIN('${id}', true)")
                .rows
                .let {
                    return if (it.isEmpty()) null
                    else map(it[0])
                }

    override suspend fun findAll(): List<UserEntity> {
        return databaseProvider
                .connectionPool
                .asSuspending
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