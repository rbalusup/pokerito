package io.toxa108.pokerito.testfrontend.server

import io.grpc.netty.NettyServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService
import io.toxa108.pokerito.testfrontend.server.service.NotificationService
import org.springframework.stereotype.Service

@Service
class Server(private val notificationService: NotificationService) {
    private val server: io.grpc.Server = NettyServerBuilder
            .forPort(7778)
            .addService(notificationService)
            .addService(ProtoReflectionService.newInstance())
            .build()

    init {
        server.start()
    }
}