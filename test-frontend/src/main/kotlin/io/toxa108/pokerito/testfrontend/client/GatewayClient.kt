package io.toxa108.pokerito.testfrontend.client

import org.springframework.stereotype.Service

@Service
class GatewayClient {
    var s: io.toxa108.pokerito.userservice.proto.UserServiceGrpc.UserServiceBlockingStub? = null

    init {
    }

}