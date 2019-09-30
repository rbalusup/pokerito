package io.toxa108.pokerito.gameservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "database")
class DatabaseConfigImpl: DatabaseConfig {
    lateinit var userName: String
    lateinit var password: String
    lateinit var host: String
    lateinit var port: String
    lateinit var databaseName: String

    override fun userName() = userName
    override fun password() = password
    override fun host() = host
    override fun port(): Int = Integer.valueOf(port)
    override fun databaseName() = databaseName
}