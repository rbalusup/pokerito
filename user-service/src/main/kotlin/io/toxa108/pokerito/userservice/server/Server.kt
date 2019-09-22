package io.toxa108.pokerito.userservice.server

import io.grpc.netty.NettyServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService
import io.toxa108.pokerito.userservice.service.UserService
import org.springframework.stereotype.Service

@Service
class Server(private val userService: UserService) {
    private val server: io.grpc.Server = NettyServerBuilder
            .forPort(15004)
            .addService(userService)
            .addService(ProtoReflectionService.newInstance())
            .build()

    init {
        server.start()
    }
}