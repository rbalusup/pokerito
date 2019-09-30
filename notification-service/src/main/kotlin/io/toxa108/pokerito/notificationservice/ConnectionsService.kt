package io.toxa108.pokerito.notificationservice

import com.google.protobuf.Empty
import io.grpc.stub.StreamObserver
import io.toxa108.pokerito.notificationservice.proto.EnterTableFinishRequest
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CountDownLatch

/**
 * Service for notification end users by grpc
 *
 * @property connectionsStorage the storage of grpc connections
 */
@Component
class ConnectionsService constructor(private val connectionsStorage: ConnectionsStorage) {
    private val logger = KotlinLogging.logger {}

    /**
     * Notify user about successful addition him to table.
     * And send notification to the logic service.
     */
    fun notifyEnterTable(userId: UUID, event: EnterTableFinishRequest) {
        val connection = connectionsStorage.get(userId)

        val finishLatch = CountDownLatch(1);
        val responseObserver = object : StreamObserver<Empty> {
            override fun onNext(response: Empty) {
            }

            override fun onError(t: Throwable) {
                logger.error(t) { "Error!!1" }
                finishLatch.countDown();
            }

            override fun onCompleted() {
                finishLatch.countDown();
            }
        }
        connection.stub.enterTable(event, responseObserver)
        finishLatch.await()
    }
}