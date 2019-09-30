package io.toxa108.pokerito.tableservice.server

import io.grpc.ServerInterceptors
import io.grpc.netty.NettyServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService
import io.toxa108.pokerito.tableservice.interceptor.AuthorizationServerInterceptor
import io.toxa108.pokerito.tableservice.service.TableService
import org.springframework.stereotype.Service

@Service
class Server(private val tableService: TableService) {
    private val server: io.grpc.Server = NettyServerBuilder
            .forPort(15005)
            .addService(ServerInterceptors.intercept(
                    tableService,
                    AuthorizationServerInterceptor()
            ))
            .addService(ProtoReflectionService.newInstance())
            .build()

    init {
        server.start()
    }
}