package io.toxa108.pokerito.testfrontend.service

import io.grpc.ManagedChannelBuilder
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.toxa108.pokerito.testfrontend.client.BearerToken
import io.toxa108.pokerito.testfrontend.client.Const
import io.toxa108.pokerito.userservice.proto.UserRequest
import io.toxa108.pokerito.userservice.proto.UserServiceGrpc
import org.springframework.stereotype.Service

@Service
class UserService constructor(private val userDataProvider: UserDataProvider){

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

        val token = Jwts.builder()
                .setPayload("123")
                .signWith(SignatureAlgorithm.HS256, Const.JWT_SIGNING_KEY)
                .compact()

        val be = BearerToken(token)
        val stub = UserServiceGrpc.newBlockingStub(channel)
                .withCallCredentials(be)

        try {

            val response = stub.auth(
                    UserRequest.newBuilder()
                            .setLogin(login)
                            .setPassword(password)
                            .build())

            userDataProvider.token = response.token
            userDataProvider.login = response.login
            userDataProvider.email = response.email
            userDataProvider.id = response.id

            println(response)
            println("Hello to the Poker Game Mr. {${userDataProvider.login}}")
        } catch (e: Throwable) {

        }
        channel.shutdown()
    }

    fun ttt(login: String, password: String) {
        val channel = ManagedChannelBuilder.forAddress("localhost", 15004)
                .usePlaintext()
                .build()

        val token = BearerToken(userDataProvider.token)

        val stub = UserServiceGrpc.newBlockingStub(channel)
                .withCallCredentials(token)
    }
}

