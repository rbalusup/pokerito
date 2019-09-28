package io.toxa108.pokerito.testfrontend.service

import io.grpc.ManagedChannelBuilder
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

        val helloResponse = stub.reg(
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
            println("Hello to the Poker Game Mr. ${userDataProvider.login}")
        } catch (e: Throwable) {
            println(e.message)
        }
        channel.shutdown()
    }
}

