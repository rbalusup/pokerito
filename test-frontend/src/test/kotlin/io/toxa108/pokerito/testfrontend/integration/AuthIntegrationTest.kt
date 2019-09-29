package io.toxa108.pokerito.testfrontend.integration

import io.toxa108.pokerito.testfrontend.service.UserDataProvider
import io.toxa108.pokerito.testfrontend.service.UserService
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class AuthIntegrationTest {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userDataProvider: UserDataProvider

    //@org.junit.Test
    fun auth_success() {
        userService.auth("toxa", "123")
        assert(userDataProvider.isAuth())
        assert(userDataProvider.login == "toxa")
    }
}