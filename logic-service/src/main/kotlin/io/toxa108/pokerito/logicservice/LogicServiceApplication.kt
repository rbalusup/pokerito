package io.toxa108.pokerito.logicservice

import io.toxa108.pokerito.logicservice.server.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LogicServiceApplication

fun main(args: Array<String>) {
    val context = runApplication<LogicServiceApplication>(*args)
    val service = context.getBean(Server::class.java)

    val i = 0
}
