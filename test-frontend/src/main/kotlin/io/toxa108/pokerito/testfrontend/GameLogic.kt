package io.toxa108.pokerito.testfrontend

import io.toxa108.pokerito.testfrontend.client.GatewayClient
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.util.*

@Component
class GameLogic constructor(private val gatewayClient: GatewayClient){

    @EventListener
    fun event(event: ApplicationReadyEvent) {
        start()
    }

    fun start() {
        println("Do you have an account? Yes / No")

        val scanner = Scanner(System.`in`)
        val answer = scanner.nextLine()

        if (answer.toLowerCase() != "yes") {
            println("Enter login:")
            val login = scanner.nextLine()
            println("Enter email:")
            val email = scanner.nextLine()
            println("Enter password:")
            val password = scanner.nextLine()

            gatewayClient.registerUser(login, email, password)
        } else {
            println("Enter login:")
            val login = scanner.nextLine()
            println("Enter password:")
            val password = scanner.nextLine()
            gatewayClient.auth(login, password)
        }
    }
}
