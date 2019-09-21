package io.toxa108.pokerito.userservice.repository.entity

import java.math.BigDecimal
import java.util.*

data class WalletEntity(
        val id: UUID,
        val amount: BigDecimal
) {
    class Builder {
        private lateinit var id: UUID
        private var amount: BigDecimal = BigDecimal.ZERO

        fun id(id: UUID) = apply { this.id = id }
        fun amount(amount: BigDecimal) = apply { this.amount = amount }

        fun build() = WalletEntity(
                id = id,
                amount = amount
        )
    }
}