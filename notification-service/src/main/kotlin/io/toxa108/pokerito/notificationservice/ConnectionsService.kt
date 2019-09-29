package io.toxa108.pokerito.notificationservice

import io.toxa108.pokerito.notificationservice.proto.EnterTableFinishRequest
import org.springframework.stereotype.Component
import java.util.*

@Component
class ConnectionsService constructor(private val connectionsStorage: ConnectionsStorage) {
    fun notifyEnterTable(userId: UUID, event: EnterTableFinishRequest) {
        val connection = connectionsStorage.get(userId)
        connection.stub.enterTable(event).get()
    }
}