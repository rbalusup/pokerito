package io.toxa108.pokerito.testfrontend

import io.toxa108.pokerito.testfrontend.service.UserService
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.util.*

@Component
class GameLogic constructor(private val userService: UserService){

    @EventListener
    fun event(event: ApplicationReadyEvent) {
        start()
    }

    fun start() {
        println("Do you have an account? Yes / No")

        val scanner = Scanner(System.`in`)
        val answer = scanner.nextLine()

        if (answer.toLowerCase() == "yes" || answer.toLowerCase() == "y") {
            println("Enter login:")
            val login = scanner.nextLine()
            println("Enter password:")
            val password = scanner.nextLine()
            userService.auth(login, password)
        } else {
            println("Enter login:")
            val login = scanner.nextLine()
            println("Enter email:")
            val email = scanner.nextLine()
            println("Enter password:")
            val password = scanner.nextLine()

            userService.registerUser(login, email, password)
        }
    }
}
