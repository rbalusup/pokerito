package io.toxa108.pokerito.gameservice

import io.toxa108.pokerito.gameservice.config.DatabaseConfig
import liquibase.integration.spring.SpringLiquibase
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import javax.sql.DataSource

@SpringBootApplication
class GameServiceApplication {
    @Bean()
    fun dataSource(databaseConfig: DatabaseConfig): DataSource {
        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver")
        dataSourceBuilder.url("jdbc:mysql://${databaseConfig.host()}:${databaseConfig.port()}/${databaseConfig.databaseName()}")
        dataSourceBuilder.username(databaseConfig.userName())
        dataSourceBuilder.password(databaseConfig.password())
        return dataSourceBuilder.build()
    }

    @Bean
    fun liquibase(dataSource: DataSource): SpringLiquibase {
        val liquibase = SpringLiquibase()
        liquibase.changeLog = "classpath:db/migrations/v1.0/db.changelog-master.yaml"
        liquibase.dataSource = dataSource
        return liquibase
    }
}

fun main(args: Array<String>) {
    runApplication<GameServiceApplication>(*args)
}
