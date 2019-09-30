package io.toxa108.pokerito.notificationservice

import io.grpc.ManagedChannelBuilder
import io.toxa108.pokerito.notificationservice.proto.NewConnectionRequest
import io.toxa108.pokerito.notificationservice.proto.NotificationServiceGrpc
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.NoSuchElementException

/**
 * Storage of grpc connections to end users
 */
@Component
class ConnectionsStorage {
    // if i were have good pc i would store data in redis))
    val map = ConcurrentHashMap<UUID, ConnectionWrapper>()

    /**
     * Add new connection to storage
     * @param connection connection metadata (ip, userId)
     */
    fun put(connection: NewConnectionRequest) {
        map[UUID.fromString(connection.userId)]?.let {
            if (it.ip == connection.ip) return
        }

        val channel = ManagedChannelBuilder.forAddress(connection.ip, 7778)
                .usePlaintext()
                .build()

        val stub = NotificationServiceGrpc.newStub(channel)
        map[UUID.fromString(connection.userId)] = ConnectionWrapper(connection.ip, stub)
    }

    /**
     * Get current connection of user
     * @param id user id
     */
    fun get(id: UUID) = map[id] ?: throw NoSuchElementException()

    /**
     * Wrapper under metadata of user and grpc stub
     */
    data class ConnectionWrapper constructor(val ip: String,
                                             val stub: NotificationServiceGrpc.NotificationServiceStub)
}