package io.toxa108.pokerito.userservice.ext

import io.toxa108.pokerito.userservice.proto.UserRequest
import io.toxa108.pokerito.userservice.proto.UserResponse
import io.toxa108.pokerito.userservice.repository.entity.UserEntity
import org.springframework.stereotype.Component
import java.util.*

// E - entity
// G - grpc

@Component
class Map {
    val userEtoG = {
        e: UserEntity -> UserResponse.newBuilder()
            .setId(e.id.toString())
            .setEmail(e.email)
            .setLogin(e.login)
            .build()
    }

    val userGtoE = {
        g: UserRequest -> UserEntity.Builder()
            .id(UUID.fromString(g.id))
            .email(g.email)
            .login(g.login)
            .password(g.password)
            .build()
    }
}