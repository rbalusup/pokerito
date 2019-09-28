package io.toxa108.pokerito.notificationservice

import io.toxa108.pokerito.notificationservice.proto.NewConnectionRequest
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.NoSuchElementException

@Component
class ConnectionsStorage {
    // if i were have good pc i would store data in redis))
    val map = ConcurrentHashMap<UUID, String>()

    fun put(m: NewConnectionRequest) {
        map[UUID.fromString(m.userId)] = m.ip
    }

    fun get(id: UUID) = map[id] ?: throw NoSuchElementException()
}