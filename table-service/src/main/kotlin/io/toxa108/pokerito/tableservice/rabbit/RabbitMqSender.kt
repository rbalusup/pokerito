package io.toxa108.pokerito.tableservice.rabbit

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class RabbitMqSender constructor(private val template: RabbitTemplate,
                                 private val queue: Queue) {
    @EventListener
    fun sendEvent(event: com.google.protobuf.GeneratedMessageV3) {
        send(event)
    }

    fun send(event: com.google.protobuf.GeneratedMessageV3) {
        template.convertAndSend(queue.name, event)
        println("[x] Sent '$event'")
    }
}