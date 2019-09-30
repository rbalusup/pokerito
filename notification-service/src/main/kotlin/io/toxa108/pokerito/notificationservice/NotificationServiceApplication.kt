package io.toxa108.pokerito.notificationservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.event.ApplicationEventMulticaster
import org.springframework.context.event.SimpleApplicationEventMulticaster
import org.springframework.core.task.SimpleAsyncTaskExecutor

@SpringBootApplication
class NotificationServiceApplication {

    @Bean(name = ["applicationEventMulticaster"])
    fun simpleApplicationEventMulticaster(): ApplicationEventMulticaster {
        val eventMulticaster = SimpleApplicationEventMulticaster()
        eventMulticaster.setTaskExecutor(SimpleAsyncTaskExecutor())
        return eventMulticaster
    }
}

fun main(args: Array<String>) {
    runApplication<NotificationServiceApplication>(*args)
}
