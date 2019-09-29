package io.toxa108.pokerito.notificationservice

import io.grpc.ManagedChannelBuilder
import io.toxa108.pokerito.notificationservice.proto.NewConnectionRequest
import io.toxa108.pokerito.notificationservice.proto.NotificationServiceGrpc
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.NoSuchElementException

@Component
class ConnectionsStorage {
    // if i were have good pc i would store data in redis))
    val map = ConcurrentHashMap<UUID, ConnectionWrapper>()

    fun put(m: NewConnectionRequest) {
        val channel = ManagedChannelBuilder.forAddress(m.ip, 7778)
                .usePlaintext()
                .build()

        val stub = NotificationServiceGrpc.newFutureStub(channel)
        map[UUID.fromString(m.userId)] = ConnectionWrapper(m.ip, stub)
    }

    fun get(id: UUID) = map[id] ?: throw NoSuchElementException()

    data class ConnectionWrapper constructor(val ip: String,
                                             val stub: NotificationServiceGrpc.NotificationServiceFutureStub) {
    }
}