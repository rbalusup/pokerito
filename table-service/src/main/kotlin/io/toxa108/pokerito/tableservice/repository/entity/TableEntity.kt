package io.toxa108.pokerito.tableservice.repository.entity

import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.util.*

data class TableEntity(
        val id: UUID,
        val gameId: UUID,
        val createTime: LocalDateTime,
        val closeTime: LocalDateTime?,
        val players: Short) {

    class Builder {
        private lateinit var id: UUID
        private lateinit var gameId: UUID
        private var createTime: LocalDateTime = LocalDateTime.now()
        private var closeTime: LocalDateTime? = null
        private var players: Short = 0

        fun id(id: UUID) = apply { this.id = id }
        fun gameId(gameId: UUID) = apply { this.gameId = gameId }
        fun createTime(createTime: LocalDateTime) = apply { this.createTime = createTime }
        fun closeTime(closeTime: LocalDateTime?) = apply { this.closeTime = closeTime }
        fun players(players: Short) = apply {
            require(players >= 0) { "Players > 0" }
            this.players = players
        }

        fun build() = TableEntity(
                id = id,
                gameId = gameId,
                createTime = createTime,
                closeTime = closeTime,
                players = players
        )
    }
}