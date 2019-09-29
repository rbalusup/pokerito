package io.toxa108.pokerito.notificationservice

import io.toxa108.pokerito.notificationservice.proto.EnterTableFinishRequest
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import java.util.*


@RabbitListener(queues = ["notification"])
@Component
class NotificationsReceiver constructor(private val connectionsService: ConnectionsService) {

    @RabbitHandler
    fun receive(message: EnterTableFinishRequest) {
        println("[x] Received '$message'")

        connectionsService.notifyEnterTable(
                UUID.fromString(message.userId),
                message
        )
    }
}