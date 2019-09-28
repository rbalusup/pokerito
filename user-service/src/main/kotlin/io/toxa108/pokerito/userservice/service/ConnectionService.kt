package io.toxa108.pokerito.userservice.service

import io.toxa108.pokerito.notificationservice.proto.NewConnectionRequest
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.net.InetAddress
import java.util.*

@Service
class ConnectionService constructor(private val applicationEventPublisher: ApplicationEventPublisher) {
    private fun ip(): String {
        val ip = InetAddress.getLocalHost()
        println("IP of my system is := " + ip.hostAddress)
        return ip.hostAddress
    }

    fun addConnection(userId: UUID) {
        val newConnection = NewConnectionRequest.newBuilder()
                .setIp(ip())
                .setUserId(userId.toString())
                .build()

        applicationEventPublisher.publishEvent(newConnection)
    }
}