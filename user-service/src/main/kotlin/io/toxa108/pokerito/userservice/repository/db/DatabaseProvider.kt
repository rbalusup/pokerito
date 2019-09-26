package io.toxa108.pokerito.userservice.repository.db

import com.github.jasync.sql.db.Configuration
import com.github.jasync.sql.db.ConnectionPoolConfigurationBuilder
import com.github.jasync.sql.db.mysql.pool.MySQLConnectionFactory
import com.github.jasync.sql.db.pool.ConnectionPool
import io.toxa108.pokerito.userservice.config.DatabaseConfig
import org.springframework.stereotype.Component

@Component
class DatabaseProvider constructor(databaseConfig: DatabaseConfig){
    private val configuration = Configuration(
            databaseConfig.userName,
            databaseConfig.host,
            Integer.valueOf(databaseConfig.port),
            databaseConfig.password,
            databaseConfig.databaseName)
    val connectionPool = ConnectionPool(
            factory = MySQLConnectionFactory(configuration),
            configuration = ConnectionPoolConfigurationBuilder(
                    username = databaseConfig.userName,
                    host = databaseConfig.host,
                    port = Integer.valueOf(databaseConfig.port),
                    password = databaseConfig.password,
                    database = databaseConfig.databaseName
            ).build()
    )
}