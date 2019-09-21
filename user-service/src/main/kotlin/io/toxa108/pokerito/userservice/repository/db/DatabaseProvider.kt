package io.toxa108.pokerito.userservice.repository.db

import com.github.jasync.sql.db.Configuration
import com.github.jasync.sql.db.Connection
import com.github.jasync.sql.db.ConnectionPoolConfigurationBuilder
import com.github.jasync.sql.db.QueryResult
import com.github.jasync.sql.db.mysql.pool.MySQLConnectionFactory
import com.github.jasync.sql.db.pool.ConnectionPool
import kotlinx.coroutines.future.await
import org.springframework.stereotype.Component

@Component
class DatabaseProvider {
    private val configuration = Configuration(
            "toxa108",
            "localhost",
            3306,
            "Bernard-74-94Q",
            "poker")
    val connectionPool = ConnectionPool(
            factory = MySQLConnectionFactory(configuration),
            configuration = ConnectionPoolConfigurationBuilder(
                    username = "toxa108",
                    host = "localhost",
                    port = 3306,
                    password = "Bernard-74-94Q",
                    database = "poker"
            ).build()
    )
}

suspend fun Connection.sendPreparedStatementAwait(
        query: String,
        values: List<Any>): QueryResult =
        sendPreparedStatement(query, values).await()

suspend fun Connection.sendPreparedStatementAwait(query: String): QueryResult
        = sendPreparedStatement(query).await()

suspend fun Connection.sendQueryAwait(query: String): QueryResult
        = sendQuery(query).await()