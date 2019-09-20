package io.toxa108.pokerito.userservice.repository

import io.toxa108.pokerito.userservice.repository.entity.UserEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
        val myScope = GlobalScope
        runBlocking {
            myScope.launch {
                val uuid = UUID.randomUUID()
                var save = repo.save(UserEntity(uuid, "fd123", "fd", "fdf", UUID.randomUUID()))

                val list = repo.findAll()
                assert(save == list.last())
                assert(1 == 2)
                repo.findById(uuid)?.id?.equals(uuid)?.let { assert(it) } ?: assert(false)
            }
        }
    }
}