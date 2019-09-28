package io.toxa108.pokerito.notificationservice

import io.toxa108.pokerito.notificationservice.proto.NewConnectionRequest
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@RabbitListener(queues = ["connection"])
@Component
class ConnectionsReceiver constructor(private val connectionsStorage: ConnectionsStorage) {

    @RabbitHandler
    fun receive(message: NewConnectionRequest) {
        connectionsStorage.put(message)
        println("[x] Received '$message'")
    }
}