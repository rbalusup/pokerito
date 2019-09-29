package io.toxa108.pokerito.testfrontend.server.service

import com.google.protobuf.Empty
import io.grpc.stub.StreamObserver
import io.toxa108.pokerito.notificationservice.proto.EnterTableFinishRequest
import io.toxa108.pokerito.notificationservice.proto.NotificationServiceGrpc
import org.springframework.stereotype.Service

@Service
class NotificationService: NotificationServiceGrpc.NotificationServiceImplBase() {
    override fun enterTable(request: EnterTableFinishRequest?, responseObserver: StreamObserver<Empty>?) {
        responseObserver?.onNext(Empty.newBuilder().build())
        responseObserver?.onCompleted()
    }
}