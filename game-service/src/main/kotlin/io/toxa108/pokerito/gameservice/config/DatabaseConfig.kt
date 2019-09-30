package io.toxa108.pokerito.gameservice.config

interface DatabaseConfig {
    fun userName(): String
    fun password(): String
    fun host(): String
    fun port(): Int
    fun databaseName(): String
}