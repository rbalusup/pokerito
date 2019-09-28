package io.toxa108.pokerito.notificationservice

import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component


@RabbitListener(queues = ["notification"])
@Component
class NotificationsReceiver {
    @RabbitHandler
    fun receive(message: ByteArray) {
        println(" [x] Received '$message'")
    }
}