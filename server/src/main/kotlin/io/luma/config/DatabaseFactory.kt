package io.luma.config

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        Database.connect(
            url = ApplicationConfig.databaseUrl,
            driver = "org.postgresql.Driver",
            user = ApplicationConfig.databaseUser,
            password = ApplicationConfig.databasePassword
        )

        transaction {
            // SchemaUtils.createSchema()
        }
    }
}
