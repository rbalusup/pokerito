package io.toxa108.pokerito.logicservice.server

import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService
import org.springframework.stereotype.Service

@Service
class Server {
    private val server: io.grpc.Server

    init {
        server = ServerBuilder
                .forPort(15002)
                .addService(ProtoReflectionService.newInstance())
                .build()
                .start()
    }
}