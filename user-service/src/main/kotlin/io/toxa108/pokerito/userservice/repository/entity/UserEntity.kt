package io.toxa108.pokerito.userservice.repository.entity

import java.math.BigDecimal
import java.util.*

data class UserEntity(
        val id: UUID,
        val email: String,
        val login: String,
        val password: String,
        val usd: BigDecimal
)