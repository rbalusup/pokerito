package io.toxa108.pokerito.gameservice.server

import io.grpc.netty.NettyServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService
import io.toxa108.pokerito.gameservice.config.DiscoveryConfig
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("local,prod")
class Server constructor(private val discoveryConfig: DiscoveryConfig){
    private val server: io.grpc.Server = NettyServerBuilder
                .forPort(discoveryConfig.gameService.port)
                .addService(ProtoReflectionService.newInstance())
                .build()

    init {
        server.start()
        server.awaitTermination()
    }
}