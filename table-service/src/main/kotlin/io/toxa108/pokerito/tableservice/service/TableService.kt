package io.toxa108.pokerito.tableservice.service

import com.google.protobuf.Empty
import io.grpc.stub.StreamObserver
import io.toxa108.pokerito.tableservice.proto.AddUserToTableRequest
import io.toxa108.pokerito.tableservice.proto.TableServiceGrpc
import io.toxa108.pokerito.tableservice.repository.TableRepository
import io.toxa108.pokerito.tableservice.repository.entity.TableEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import java.util.*

@Service
class TableService constructor(private val tableRepository: TableRepository)
    : TableServiceGrpc.TableServiceImplBase() {

    private final val job = SupervisorJob()
    private final val scope = CoroutineScope(Dispatchers.Default + job)

    override fun addUserToTable(request: AddUserToTableRequest?, responseObserver: StreamObserver<Empty>?) {
        request?.let {
            // todo get game id from game service
            val gameId = UUID.randomUUID()
            val id = if (it.tableId != null) UUID.fromString(it.tableId) else UUID.randomUUID()

            scope.launch {
                tableRepository.save(TableEntity.Builder()
                        .id(id)
                        .gameId(gameId)
                        .build()
                )

                // todo send notification to user that user was sit to the table
                // send event to RabbitMQ. Notification service read this event and send such event.
            }
        }

        // error
    }
}