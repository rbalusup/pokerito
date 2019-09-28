package io.toxa108.pokerito.tableservice

import liquibase.integration.spring.SpringLiquibase
import org.springframework.amqp.core.Queue
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.event.ApplicationEventMulticaster
import org.springframework.context.event.SimpleApplicationEventMulticaster
import org.springframework.core.task.SimpleAsyncTaskExecutor
import javax.sql.DataSource


@SpringBootApplication
class TableServiceApplication{

    @Bean()
    fun getDataSource(): DataSource {
        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver")
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/poker")
        dataSourceBuilder.username("toxa108")
        dataSourceBuilder.password("Bernard-74-94Q")
        return dataSourceBuilder.build()
    }

    @Bean
    fun liquibase(dataSource: DataSource): SpringLiquibase {
        val liquibase = SpringLiquibase()
        liquibase.changeLog = "classpath:db/migrations/v1.0/db.changelog-master.yaml"
        liquibase.dataSource = dataSource
        return liquibase
    }

    @Bean
    fun notificationQueue(): Queue {
        return Queue("notification")
    }

    @Bean(name = ["applicationEventMulticaster"])
    fun simpleApplicationEventMulticaster(): ApplicationEventMulticaster {
        val eventMulticaster = SimpleApplicationEventMulticaster()
        eventMulticaster.setTaskExecutor(SimpleAsyncTaskExecutor())
        return eventMulticaster
    }
}

fun main(args: Array<String>) {
    runApplication<TableServiceApplication>(*args)
}
