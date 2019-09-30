package io.toxa108.pokerito.gameservice.repository.entity

import java.time.LocalDateTime
import java.util.*

data class TournamentEntity(
        val id: UUID,
        val type: Type,
        val createTime: LocalDateTime,
        val closeTime: LocalDateTime?) {

    class Builder {
        private lateinit var id: UUID
        private lateinit var type: Type
        private var createTime: LocalDateTime = LocalDateTime.now()
        private var closeTime: LocalDateTime? = null

        fun id(id: UUID) = apply { this.id = id }
        fun type(type: Type) = apply { this.type = type }
        fun createTime(createTime: LocalDateTime) = apply { this.createTime = createTime }
        fun closeTime(closeTime: LocalDateTime?) = apply { this.closeTime = closeTime }

        fun build() = TournamentEntity(
                id = id,
                type = type,
                createTime = createTime,
                closeTime = closeTime
        )
    }

    enum class Type { CASH, TOURNAMENT }
}