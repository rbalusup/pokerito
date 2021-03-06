package io.toxa108.pokerito.userservice

import io.toxa108.pokerito.userservice.config.DatabaseConfig
import liquibase.integration.spring.SpringLiquibase
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import javax.sql.DataSource

@SpringBootApplication
class UserServiceApplication {

    @Bean()
//    @Profile("!test")
    fun getDataSource(databaseConfig: DatabaseConfig): DataSource {
        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver")
        dataSourceBuilder.url("jdbc:mysql://${databaseConfig.host}:${databaseConfig.port}/${databaseConfig.databaseName}")
        dataSourceBuilder.username(databaseConfig.userName)
        dataSourceBuilder.password(databaseConfig.password)

        return dataSourceBuilder.build()
    }

//    @Bean
//    @Profile("test")
    fun getDataSourceTest(): DataSource {
        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.driverClassName("org.h2.Driver")
        dataSourceBuilder.url("jdbc:h2:mem:test")
        dataSourceBuilder.username("SA")
        dataSourceBuilder.password("")
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
    runApplication<UserServiceApplication>(*args)
}
