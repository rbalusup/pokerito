package io.toxa108.pokerito.userservice.repository

import io.toxa108.pokerito.userservice.repository.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private lateinit var repo: UserRepository

    @Test
    @DisplayName("UserRepositoryTest")
    fun test() {
        runBlocking {
            val uuid = UUID.randomUUID()
            withContext(Dispatchers.Default) {
                repo.deleteAll()
            }
            withContext(Dispatchers.Default) {
                repo.save(UserEntity(uuid, "fd123", "fd", "fdf", UUID.randomUUID()))
            }

            val list = withContext(Dispatchers.Default) { repo.findAll() }
            assert (list.size == 1)

            val founded = withContext(Dispatchers.Default) { repo.findById(uuid) }
            assert(founded?.id == uuid)
        }
    }
}