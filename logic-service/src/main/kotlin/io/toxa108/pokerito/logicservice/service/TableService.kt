package io.toxa108.pokerito.logicservice.service

import io.grpc.stub.StreamObserver
import io.toxa108.pokerito.logicservice.proto.TableRequest
import io.toxa108.pokerito.logicservice.proto.TableResponse
import io.toxa108.pokerito.logicservice.proto.TableServiceGrpc
import org.springframework.stereotype.Service

@Service
class TableService : TableServiceGrpc.TableServiceImplBase() {
    override fun createTable(request: TableRequest?, responseObserver: StreamObserver<TableResponse>?) {
        super.createTable(request, responseObserver)

        val tableResponse = TableResponse
                .newBuilder()
                .setName("table1")
                .build();

        responseObserver?.onNext(tableResponse)
        responseObserver?.onCompleted()
    }
}