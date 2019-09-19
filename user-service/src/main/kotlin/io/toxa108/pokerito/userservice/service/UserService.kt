package io.toxa108.pokerito.userservice.service

import io.grpc.stub.StreamObserver
import io.toxa108.pokerito.userservice.proto.UserRequest
import io.toxa108.pokerito.userservice.proto.UserResponse
import io.toxa108.pokerito.userservice.proto.UserServiceGrpc
import io.toxa108.pokerito.userservice.repository.UserRepository
import io.toxa108.pokerito.userservice.repository.entity.UserEntity
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val repo: UserRepository) : UserServiceGrpc.UserServiceImplBase() {

    override fun create(request: UserRequest?, responseObserver: StreamObserver<UserResponse>?) {
        super.create(request, responseObserver)

        val id = UUID.randomUUID();

        val save = GlobalScope.async(start = CoroutineStart.LAZY) {
            request?.let {
                repo.save(UserEntity(id, it.email, it.login, it.password))
            }
        }

        GlobalScope.launch {
            save.await()
        }

        val user = UserResponse.newBuilder()
                .setId(id.toString())
                .setEmail(request?.email)
                .setLogin(request?.login)
                .build();

        responseObserver?.onNext(user)
        responseObserver?.onCompleted()
    }
}