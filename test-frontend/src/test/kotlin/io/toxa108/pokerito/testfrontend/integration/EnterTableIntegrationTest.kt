package io.toxa108.pokerito.testfrontend.integration

import io.toxa108.pokerito.testfrontend.service.TableService
import io.toxa108.pokerito.testfrontend.service.UserDataProvider
import io.toxa108.pokerito.testfrontend.service.UserService
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class EnterTableIntegrationTest {
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var tableService: TableService

    @Autowired
    lateinit var userDataProvider: UserDataProvider

    @org.junit.Test
    fun enter_to_table_success() {
        userService.auth("toxa", "123")
        assert(userDataProvider.isAuth())
        assert(userDataProvider.login == "toxa")

        tableService.addUserToTable()
    }
}