package io.toxa108.pokerito.logicservice.server

import io.grpc.ServerBuilder
import io.toxa108.pokerito.logicservice.service.TableService
import org.springframework.stereotype.Service

@Service
class Server(private val tableService: TableService) {
    private val server: io.grpc.Server

    init {
        server = ServerBuilder
                .forPort(15001)
                .addService(tableService)
                .build()
    }


}