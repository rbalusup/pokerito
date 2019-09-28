package io.toxa108.pokerito.userservice.service

import io.grpc.Status
import io.grpc.stub.StreamObserver
import io.toxa108.pokerito.userservice.proto.AuthResponse
import io.toxa108.pokerito.userservice.proto.UserRequest
import io.toxa108.pokerito.userservice.proto.UserResponse
import io.toxa108.pokerito.userservice.proto.UserServiceGrpc
import io.toxa108.pokerito.userservice.repository.UserRepository
import io.toxa108.pokerito.userservice.repository.WalletRepository
import io.toxa108.pokerito.userservice.repository.entity.UserEntity
import io.toxa108.pokerito.userservice.repository.entity.WalletEntity
import kotlinx.coroutines.*
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class UserService(private val userRepository: UserRepository,
                  private val walletRepository: WalletRepository,
                  private val map: io.toxa108.pokerito.userservice.ext.Map,
                  private val authService: AuthService,
                  private val connectionService: ConnectionService
) : UserServiceGrpc.UserServiceImplBase() {

    private final val job = SupervisorJob()
    private final val scope = CoroutineScope(Dispatchers.Default + job)

    override fun reg(request: UserRequest?, responseObserver: StreamObserver<UserResponse>?) {
        val id = UUID.randomUUID();

        request?.let {
            scope.launch {
                withContext (Dispatchers.Default) {
                    val walletId = UUID.randomUUID()

                    walletRepository.save(
                            WalletEntity.Builder()
                                    .id(walletId)
                                    .amount(BigDecimal.ZERO)
                                    .build()
                    )
                    userRepository.save(UserEntity(id, it.email, it.login, it.password, walletId))
                }
            }

            runBlocking {
                val user = userRepository.findByLoginOrEmail(request.login, request.email)
                if (user != null) {
                    responseObserver?.onError(Status.INVALID_ARGUMENT
                            .withDescription("User with such login or email already exists")
                            .asRuntimeException()
                    )
                }
            }

            val user = UserResponse.newBuilder()
                    .setId(id.toString())
                    .setEmail(request.email)
                    .setLogin(request.login)
                    .build();

            connectionService.addConnection(id)

            responseObserver?.onNext(user)
            responseObserver?.onCompleted()
        }

        responseObserver?.onError(Status.INVALID_ARGUMENT
                .asRuntimeException()
        )
    }

    override fun get(request: UserRequest?, responseObserver: StreamObserver<UserResponse>?) {
        request?.let {

            scope.launch {
                val entity = withContext(Dispatchers.Default) { userRepository.findById(UUID.fromString(it.id)) }

                if (entity != null) {
                    responseObserver?.onNext(map.userEtoG.invoke(entity))
                    responseObserver?.onCompleted()
                }
            }
        }
    }

    override fun auth(request: UserRequest?, responseObserver: StreamObserver<AuthResponse>?) {
        request?.let {
            runBlocking {
                val entity = userRepository.findByLogin(it.login)
                if (entity != null && entity.password == it.password) {
                    val token = authService.generateToken(entity.id)

                    connectionService.addConnection(entity.id)

                    responseObserver?.onNext(AuthResponse.newBuilder()
                            .setToken(token)
                            .setId(entity.id.toString())
                            .setLogin(entity.login)
                            .setEmail(entity.email)
                            .build()
                    )
                    responseObserver?.onCompleted()
                } else {
                    responseObserver?.onError(Status.PERMISSION_DENIED
                            .withDescription("Incorrect login or password!")
                            .asRuntimeException())
                }
            }
        } ?: responseObserver?.onError(Status.PERMISSION_DENIED
                    .withDescription("Incorrect login or password!")
                    .asRuntimeException())
    }
}