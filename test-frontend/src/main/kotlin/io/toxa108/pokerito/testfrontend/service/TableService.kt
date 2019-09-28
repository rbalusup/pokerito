package io.toxa108.pokerito.testfrontend.service

import io.grpc.ManagedChannelBuilder
import io.toxa108.pokerito.tableservice.proto.AddUserToTableRequest
import io.toxa108.pokerito.tableservice.proto.TableServiceGrpc
import io.toxa108.pokerito.testfrontend.client.BearerToken
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

        val request = AddUserToTableRequest.newBuilder()
                .setUserId(userDataProvider.id)
                .build()

        val response = stub.addUserToTable(request)
        println(response)
    }
}

