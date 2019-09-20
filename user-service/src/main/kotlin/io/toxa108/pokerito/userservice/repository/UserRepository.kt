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
        val r = databaseProvider
                .connectionPool.sendQuery(
                    "insert into $TABLE_NAME (id, email, login, password, walletId) values " +
                            "(UNHEX(REPLACE('${data.id}', '-', '')), \"${data.email}\", \"${data.login}\", \"${data.password}\", UNHEX(REPLACE('${data.walletId}', '-', '')));"
        ).get()

        return r.rows?.get(0)?.let { map (it) } ?: throw DBConnectException("err")
    }

    override suspend fun update(data: UserEntity): UserEntity {
        return UserEntity(UUID.randomUUID(), "fd", "fd", "fdf", UUID.randomUUID())
    }

    override suspend fun delete(id: UUID) {
    }

    override suspend fun findById(id: UUID): UserEntity? {
        return null
    }

    override suspend fun findAll(): List<UserEntity> {
        return databaseProvider
                .connectionPool
                .sendPreparedStatement("select * from $TABLE_NAME")
                .get()
                .rows
                ?.stream()
                ?.map { r -> map(r) }
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