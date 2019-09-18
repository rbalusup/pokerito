package io.toxa108.pokerito.logicservice.repository

import io.toxa108.pokerito.logicservice.repository.entity.TableEntity
import org.springframework.stereotype.Repository
import java.util.*
import java.util.stream.Collectors.toList
import kotlin.collections.HashMap

@Repository
class TableRepository: MemoryRepository<UUID, TableEntity> {
    private val database: HashMap<UUID, TableEntity> = HashMap()

    override suspend fun save(data: TableEntity) {
        database[data.id] = data
    }

    override suspend fun byId(id: UUID): TableEntity? {
        return database[id]
    }

    override suspend fun all(): List<TableEntity> {
        return database.toList()
                .stream().map { it.second }
                .collect(toList())
    }
}