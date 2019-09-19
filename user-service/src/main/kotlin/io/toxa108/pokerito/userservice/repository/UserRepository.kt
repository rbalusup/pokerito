package io.toxa108.pokerito.userservice.repository

import io.toxa108.pokerito.userservice.repository.entity.UserEntity
import org.springframework.stereotype.Repository
import java.util.*
import java.util.stream.Collectors.toList
import kotlin.collections.HashMap

@Repository
class UserRepository: MemoryRepository<UUID, UserEntity> {
    private val database: HashMap<UUID, UserEntity> = HashMap()

    override suspend fun save(data: UserEntity) {
        database[data.id] = data
    }

    override suspend fun byId(id: UUID): UserEntity? {
        return database[id]
    }

    override suspend fun all(): List<UserEntity> {
        return database.toList()
                .stream().map { it.second }
                .collect(toList())
    }
}