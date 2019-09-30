package io.toxa108.pokerito.gameservice.server

import io.grpc.netty.NettyServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService
import org.springframework.stereotype.Service

@Service
class Server {
    private val server: io.grpc.Server

    init {
        server = NettyServerBuilder
                    .forPort(15002)
                    .addService(ProtoReflectionService.newInstance())
                    .build()
                    .start()

        server.awaitTermination()
    }
}