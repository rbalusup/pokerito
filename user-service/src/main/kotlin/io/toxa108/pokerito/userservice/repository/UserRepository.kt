package io.toxa108.pokerito.userservice.repository

import com.github.jasync.sql.db.RowData
import io.toxa108.pokerito.userservice.repository.entity.UserEntity
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors.toList

@Component
class UserRepository(private val databaseProvider: DatabaseProvider): Repository<UUID, UserEntity> {

    companion object {
        const val TABLE_NAME = "poker_user"
    }

    override suspend fun save(data: UserEntity): UserEntity {
        databaseProvider
                .connectionPool.sendPreparedStatementAwait(
                "insert into $TABLE_NAME (id, email, login, password, walletId) values " +
                        "(UNHEX(REPLACE('${data.id}', '-', '')), \"${data.email}\", \"${data.login}\", \"${data.password}\", UNHEX(REPLACE('${data.walletId}', '-', '')));")
                .rows.let {
                    if (it.isEmpty()) throw DBConnectException("err")
                    else return map(it[0])
                }
    }

    override suspend fun update(data: UserEntity): UserEntity {
        databaseProvider
                .connectionPool.sendPreparedStatementAwait(
                "update $TABLE_NAME email = \"${data.email}\", login = \"${data.login}\", password = \"${data.password}\", walletId = UNHEX(REPLACE('${data.walletId}', '-', ''))")
                .rows.let {
                    if (it.isEmpty()) throw DBConnectException("err")
                    else return map(it[0])
                }
    }

    override suspend fun delete(id: UUID) {
        databaseProvider
                .connectionPool
                .sendPreparedStatementAwait("delete from $TABLE_NAME where id = UNHEX(REPLACE('${id}', '-', ''))")
    }

    override suspend fun deleteAll() {
        databaseProvider
                .connectionPool
                .sendPreparedStatementAwait("delete from $TABLE_NAME")
    }

    override suspend fun findById(id: UUID): UserEntity? {
        return databaseProvider
                .connectionPool
                .sendPreparedStatementAwait("select * from $TABLE_NAME where id = UNHEX(REPLACE('${id}', '-', ''))")
                .rows
                .let {
                    return if (it.isEmpty()) null
                    else map(it[0])
                }
    }

    override suspend fun findAll(): List<UserEntity> {
        return databaseProvider
                .connectionPool
                .sendPreparedStatementAwait("select * from $TABLE_NAME")
                .rows.stream().map { r -> map(r) }
                ?.collect(toList())?.let { it as List<UserEntity> } ?: listOf()
    }

    private fun map (rowData: RowData): UserEntity {
        return UserEntity.Builder()
                .id(UUID.nameUUIDFromBytes(rowData["id"] as ByteArray))
                .email(rowData["email"] as String)
                .login(rowData["login"] as String)
                .password(rowData["password"] as String)
                .walletId(UUID.nameUUIDFromBytes(rowData["walletId"] as ByteArray))
                .build()
    }
}