package io.toxa108.pokerito.tableservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "database")
class DatabaseConfig {
    lateinit var userName: String
    lateinit var password: String
    lateinit var host: String
    lateinit var port: String
    lateinit var databaseName: String
}