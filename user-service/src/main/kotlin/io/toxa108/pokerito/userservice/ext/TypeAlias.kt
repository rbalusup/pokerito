package io.toxa108.pokerito.userservice.ext

import io.toxa108.pokerito.userservice.proto.UserResponse
import io.toxa108.pokerito.userservice.repository.entity.UserEntity

typealias UserMapperFromEntityToGRPC = (UserEntity) -> UserResponse