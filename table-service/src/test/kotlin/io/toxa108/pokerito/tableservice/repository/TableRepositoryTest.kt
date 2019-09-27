package io.toxa108.pokerito.tableservice.repository

import io.toxa108.pokerito.tableservice.repository.entity.TableEntity
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
class TableRepositoryTest {

    @Autowired
    private lateinit var repo: TableRepository

    @Test
    fun insert_success() {
        runBlocking {
            val id = UUID.randomUUID()
            val gameId = UUID.randomUUID()

            val s = TableEntity.Builder()
                    .id(id)
                    .gameId(gameId)
                    .createTime(LocalDateTime.now())
                    .closeTime(null)
                    .players(0)
                    .build()

            repo.save(s)

            val e = repo.findById(id)

            assert(repo.findAll().size == 1)
            assert(s == e)
        }
    }

    @Test
    fun insert_fail() {
        runBlocking {
            val id = UUID.randomUUID()
            val gameId = UUID.randomUUID()

            Assertions.assertThrows(IllegalArgumentException::class.java) {
                runBlocking {
                    repo.save(TableEntity.Builder()
                            .id(id)
                            .gameId(gameId)
                            .createTime(LocalDateTime.now())
                            .closeTime(null)
                            .players(-1)
                            .build())
                }
            }
        }
    }

}