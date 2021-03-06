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
            val walletId = UUID.randomUUID()
            val email = "chexovlixe@email.com"
            val login = "toxa109"
            val password = "123321"
            val testUserEntity = UserEntity(uuid, email, login, password, walletId)

            withContext(Dispatchers.Default) { repo.deleteAll() }
            withContext(Dispatchers.Default) { repo.save(testUserEntity) }
            val list = withContext(Dispatchers.Default) { repo.findAll() }
            val founded = withContext(Dispatchers.Default) { repo.findById(uuid) }

            assert (list.size == 1)
            assert(founded?.id == uuid)
            assert(founded?.email == email)
            assert(founded?.login == login)
            assert(founded?.password == password)
            assert(founded?.walletId == walletId)
            assert(founded!! == testUserEntity)
        }
    }
}