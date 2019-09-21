package io.toxa108.pokerito.userservice.repository.entity

import java.util.*

data class UserEntity(
        val id: UUID,
        val email: String,
        val login: String,
        val password: String,
        val walletId: UUID) {

    class Builder {
        private lateinit var id: UUID
        private var email: String = ""
        private var login: String = ""
        private var password: String = ""
        private lateinit var walletId: UUID

        fun id(id: UUID) = apply { this.id = id }
        fun email(email: String) = apply { this.email = email }
        fun login(login: String) = apply { this.login = login }
        fun password(password: String) = apply { this.password = password }
        fun walletId(walletId: UUID) = apply { this.walletId = walletId }

        fun build() = UserEntity(
                id = id,
                email = email,
                login = login,
                password = password,
                walletId = walletId
        )
    }
}