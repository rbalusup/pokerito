package io.toxa108.pokerito.testfrontend

import io.toxa108.pokerito.testfrontend.service.TableService
import io.toxa108.pokerito.testfrontend.service.UserDataProvider
import io.toxa108.pokerito.testfrontend.service.UserService
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.util.*

@Component
class GameLogic constructor(
        private val userService: UserService,
        private val userDataProvider: UserDataProvider,
        private val tableService: TableService)
{

    @EventListener
    fun event(event: ApplicationReadyEvent) {
        while (true) {
            start()
        }
    }

    fun start() {
        if (!userDataProvider.isAuth()) {
            println("Do you have an account? Yes / No")
            val scanner = Scanner(System.`in`)
            var answer = scanner.nextLine()

            if (answer.isAnswerYes()) {
                auth()
            } else {
                reg()
            }
        } else {
            println("Do you want to sit to the table? Yes / No")
            val scanner = Scanner(System.`in`)
            var answer = scanner.nextLine()

            if (answer.isAnswerYes()) {
                println("Sitting up...")
                tableService.addUserToTable()
            }
        }
    }

    fun String.isAnswerYes(): Boolean {
        return this.toLowerCase() == "yes" || this.toLowerCase() == "y"
    }

    fun auth() {
        val scanner = Scanner(System.`in`)
        println("Enter login:")
        val login = scanner.nextLine()
        println("Enter password:")
        val password = scanner.nextLine()
        userService.auth(login, password)
    }

    fun reg() {
        val scanner = Scanner(System.`in`)
        println("Enter login:")
        val login = scanner.nextLine()
        println("Enter email:")
        val email = scanner.nextLine()
        println("Enter password:")
        val password = scanner.nextLine()
        userService.registerUser(login, email, password)
    }
}
