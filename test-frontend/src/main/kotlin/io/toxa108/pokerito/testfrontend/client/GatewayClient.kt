package io.toxa108.pokerito.testfrontend.client

import io.grpc.ManagedChannelBuilder
import io.toxa108.pokerito.userservice.proto.UserRequest
import io.toxa108.pokerito.userservice.proto.UserServiceGrpc
import org.springframework.stereotype.Service

@Service
class GatewayClient {

    init {
        val channel = ManagedChannelBuilder.forAddress("localhost", 15004)
                .usePlaintext()
                .build()

        val stub = UserServiceGrpc.newBlockingStub(channel)

        val helloResponse = stub.create(
                UserRequest.newBuilder()
                        .setEmail("antonskubiiev@gmail.com")
                        .setLogin("toxa108")
                        .setPassword("bernard94")
                        .build())

        println(helloResponse)

        channel.shutdown()
    }

}

