package io.toxa108.pokerito.testfrontend.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "discovery")
class DiscoveryConfig {
    var userService: Data = Data()
    var gameService: Data = Data()
    var tableService: Data = Data()

    class Data {
        lateinit var ip: String
        var port: Int = 0
    }
}