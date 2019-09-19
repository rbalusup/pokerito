package io.toxa108.pokerito.userservice.service

import io.grpc.stub.StreamObserver
import io.toxa108.pokerito.userservice.proto.UserRequest
import io.toxa108.pokerito.userservice.proto.UserResponse
import io.toxa108.pokerito.userservice.proto.UserServiceGrpc
import io.toxa108.pokerito.userservice.repository.UserRepository
import io.toxa108.pokerito.userservice.repository.entity.UserEntity
import kotlinx.coroutines.*
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

typealias UserMapperFromEntityToGRPC = (UserEntity) -> UserResponse

@Service
class UserService(private val repo: UserRepository) : UserServiceGrpc.UserServiceImplBase() {

    private final val job = SupervisorJob()
    val scope = CoroutineScope(Dispatchers.Default + job)

    init {
        scope.launch {
            repo.save(UserEntity(
                    UUID.fromString("fb38077b-9d02-40bf-88cc-c19b4b69a2f4"),
                    "dffd",
                    "fdfd",
                    "fdfd",
                    BigDecimal.ZERO)
            )
        }
    }

    override fun create(request: UserRequest?, responseObserver: StreamObserver<UserResponse>?) {
        val id = UUID.randomUUID();

        val save = GlobalScope.async(start = CoroutineStart.LAZY) {
            request?.let {
                repo.save(UserEntity(id, it.email, it.login, it.password, BigDecimal.ZERO))
            }
        }

        scope.launch {
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

    override fun get(request: UserRequest?, responseObserver: StreamObserver<UserResponse>?) {
        request?.let {
            val map: UserMapperFromEntityToGRPC = {
                from -> UserResponse.newBuilder()
                    .setId(from.id.toString())
                    .setEmail(from.email)
                    .setLogin(from.login)
                    .build()
            }

            scope.launch {
                val entity = withContext(Dispatchers.Default) { repo.byId(UUID.fromString(it.id)) }

                if (entity != null) {
                    responseObserver?.onNext(map.invoke(entity))
                    responseObserver?.onCompleted()
                }
            }
        }
    }
}