package io.toxa108.pokerito.logicservice.repository

import io.toxa108.pokerito.logicservice.repository.entity.TableEntity
import org.junit.Before
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(value = [SpringExtension::class])
class TableRepositoryTest {

    @Autowired
    private val tableRepository: TableRepository? = null

    @Before
    suspend fun init() {
        tableRepository?.save(TableEntity(UUID.randomUUID()))
    }

    @Test
    @DisplayName("TableRepositoryTest")
    suspend fun test() {
        assertEquals(tableRepository?.all()?.size, 1)
    }
}