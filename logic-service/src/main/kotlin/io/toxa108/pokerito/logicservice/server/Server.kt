package io.toxa108.pokerito.logicservice.server

import io.grpc.ServerBuilder
import org.springframework.stereotype.Component

@Component
class Server {
    val server: io.grpc.Server = ServerBuilder
            .forPort(15001)
//                .addService(ta)
            .build()

}