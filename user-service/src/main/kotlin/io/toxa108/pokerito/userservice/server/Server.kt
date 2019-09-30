package io.toxa108.pokerito.userservice.server

import io.grpc.netty.NettyServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService
import io.toxa108.pokerito.userservice.config.DiscoveryConfig
import io.toxa108.pokerito.userservice.server.interceptor.AuthorizationServerInterceptor
import io.toxa108.pokerito.userservice.service.UserService
import org.springframework.stereotype.Service

@Service
class Server(private val userService: UserService,
             private val discoveryConfig: DiscoveryConfig) {
    private val server: io.grpc.Server = NettyServerBuilder
            .forPort(discoveryConfig.userService.port)
            .addService(userService)
            .intercept(AuthorizationServerInterceptor())
            .addService(ProtoReflectionService.newInstance())
            .build()

    init {
        server.start()
        server.awaitTermination()
    }
}