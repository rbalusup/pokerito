package io.toxa108.pokerito.userservice.rabbit

import io.toxa108.pokerito.notificationservice.proto.NewConnectionRequest
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class RabbitMqConnectionsSender constructor(private val template: RabbitTemplate) {
    private val queue = Queue("connection")

    @EventListener
    fun sendEvent(event: NewConnectionRequest) {
        send(event)
    }

    fun send(event: com.google.protobuf.GeneratedMessageV3) {
        template.convertAndSend(queue.name, event)
        println("[x] Sent '$event'")
    }
}