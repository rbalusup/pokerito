package io.toxa108.pokerito.userservice.service

import io.grpc.stub.StreamObserver
import io.toxa108.pokerito.userservice.proto.UserRequest
import io.toxa108.pokerito.userservice.proto.UserResponse
import io.toxa108.pokerito.userservice.proto.UserServiceGrpc
import io.toxa108.pokerito.userservice.repository.UserRepository
import io.toxa108.pokerito.userservice.repository.entity.UserEntity
import kotlinx.coroutines.*
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserService(private val repo: UserRepository,
                  private val map: io.toxa108.pokerito.userservice.ext.Map
) : UserServiceGrpc.UserServiceImplBase() {

    private final val job = SupervisorJob()
    private final val scope = CoroutineScope(Dispatchers.Default + job)

    override fun create(request: UserRequest?, responseObserver: StreamObserver<UserResponse>?) {
        val id = UUID.randomUUID();

        request?.let {
            scope.launch {
                withContext (Dispatchers.Default) {
                    repo.save(UserEntity(id, it.email, it.login, it.password, UUID.randomUUID()))
                }
            }
        }

        val user = UserResponse.newBuilder()
                .setId(id.toString())
                .setEmail(request?.email)
                .setLogin(request?.login)
                .build();

        responseObserver?.onNext(user)
        responseObserver?.onCompleted()
    }

    override fun get(request: UserRequest?, responseObserver: StreamObserver<UserResponse>?) {
        request?.let {

            scope.launch {
                val entity = withContext(Dispatchers.Default) { repo.findById(UUID.fromString(it.id)) }

                if (entity != null) {
                    responseObserver?.onNext(map.userEtoG.invoke(entity))
                    responseObserver?.onCompleted()
                }
            }
        }
    }
}