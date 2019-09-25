package io.toxa108.pokerito.testfrontend.client

import io.grpc.ManagedChannelBuilder
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.toxa108.pokerito.userservice.proto.UserRequest
import io.toxa108.pokerito.userservice.proto.UserServiceGrpc
import org.springframework.stereotype.Service

@Service
class GatewayClient {
    fun registerUser(login: String, email: String, password: String) {
        val channel = ManagedChannelBuilder.forAddress("localhost", 15004)
                .usePlaintext()
                .build()

        val stub = UserServiceGrpc.newBlockingStub(channel)

        val helloResponse = stub.create(
                UserRequest.newBuilder()
                        .setEmail(email)
                        .setLogin(login)
                        .setPassword(password)
                        .build())

        println(helloResponse)
        channel.shutdown()
    }

    fun auth(login: String, password: String) {
        val channel = ManagedChannelBuilder.forAddress("localhost", 15004)
                .usePlaintext()
                .build()

        val stub = UserServiceGrpc.newBlockingStub(channel)

        val response = stub.auth(
                UserRequest.newBuilder()
                        .setLogin(login)
                        .setPassword(password)
                        .build())

        println(response)
        channel.shutdown()
    }

    fun ttt(login: String, password: String) {
        val channel = ManagedChannelBuilder.forAddress("localhost", 15004)
                .usePlaintext()
                .build()

        val token = BearerToken(getJwt())

        val stub = UserServiceGrpc.newBlockingStub(channel)
                .withCallCredentials(token)

    }
    private fun getJwt(): String {
        return Jwts.builder()
                .setSubject("UserClient")
                .signWith(SignatureAlgorithm.HS256, Const.JWT_SIGNING_KEY)
                .compact()
    }
}

