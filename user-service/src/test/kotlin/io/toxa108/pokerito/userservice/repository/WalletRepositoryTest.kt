package io.toxa108.pokerito.userservice.repository

import io.toxa108.pokerito.userservice.repository.entity.WalletEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
class WalletRepositoryTest {

    @Autowired
    private lateinit var repo: WalletRepository

    @Test
    @DisplayName("WalletRepositoryTest")
    fun test() {
        runBlocking {
            val uuid = UUID.randomUUID()
            val amount = BigDecimal.valueOf(100.100)
            val testUserEntity = WalletEntity(uuid, amount)

            withContext(Dispatchers.Default) { repo.deleteAll() }
            withContext(Dispatchers.Default) { repo.save(testUserEntity) }
            val list = withContext(Dispatchers.Default) { repo.findAll() }
            val founded = withContext(Dispatchers.Default) { repo.findById(uuid) }

            assert (list.size == 1)
            assert(founded?.id == uuid)
            assert(founded?.amount == amount)
            assert(founded!! == testUserEntity)
        }
    }
}