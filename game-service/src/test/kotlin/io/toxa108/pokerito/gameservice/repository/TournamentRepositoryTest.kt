package io.toxa108.pokerito.gameservice.repository

import io.toxa108.pokerito.gameservice.repository.entity.TournamentEntity
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDateTime
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("test")
class TournamentRepositoryTest {

    @Autowired
    private lateinit var repo: TournamentRepository

    @Test
    @DisplayName("TournamentRepositoryTest")
    fun save_tournament_success() {
        runBlocking {
            val uuid = UUID.randomUUID()
            val type = TournamentEntity.Type.CASH
            val testEntity = TournamentEntity.Builder()
                    .id(uuid)
                    .type(type)
                    .createTime(LocalDateTime.now())
                    .build()

            repo.save(testEntity)
            assert(repo.countByCashType() == 1)
        }
    }
}