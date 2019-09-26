package io.toxa108.pokerito.testfrontend.service

import io.grpc.ManagedChannelBuilder
import io.toxa108.pokerito.logicservice.proto.AddUserToTableRequest
import io.toxa108.pokerito.logicservice.proto.TableServiceGrpc
import io.toxa108.pokerito.testfrontend.client.BearerToken
import io.toxa108.pokerito.userservice.proto.UserRequest
import io.toxa108.pokerito.userservice.proto.UserServiceGrpc
import org.springframework.stereotype.Service

@Service
class TableService constructor(private val userDataProvider: UserDataProvider){

    fun addUserToTable() {
        val channel = ManagedChannelBuilder.forAddress("localhost", 15005)
                .usePlaintext()
                .build()

        val token = BearerToken(userDataProvider.token)

        val stub = TableServiceGrpc.newBlockingStub(channel)
                .withCallCredentials(token)

        val response = stub.addUserToTable(AddUserToTableRequest.newBuilder().build())
        println(response)
    }
}

