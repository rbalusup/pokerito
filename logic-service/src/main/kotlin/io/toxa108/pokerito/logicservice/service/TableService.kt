package io.toxa108.pokerito.logicservice.service

import io.grpc.stub.StreamObserver
import io.toxa108.pokerito.logicservice.proto.TableRequest
import io.toxa108.pokerito.logicservice.proto.TableResponse
import io.toxa108.pokerito.logicservice.proto.TableServiceGrpc
import org.springframework.stereotype.Service
import java.util.*

@Service
class TableService : TableServiceGrpc.TableServiceImplBase() {
    override fun createTable(request: TableRequest?, responseObserver: StreamObserver<TableResponse>?) {
        super.createTable(request, responseObserver)

        val id = UUID.randomUUID()
        val tableResponse = TableResponse
                .newBuilder()
                .setId(id.toString())
                .setName("table1")
                .build();

        responseObserver?.onNext(tableResponse)
        responseObserver?.onCompleted()
    }
}