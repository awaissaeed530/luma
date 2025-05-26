package io.luma.config

import io.github.cdimascio.dotenv.dotenv

object ApplicationConfig {
    val databaseUrl: String = dotenv().get("DATABASE_URL")
    val databaseUser: String = dotenv().get("DATABASE_USER")
    val databasePassword: String = dotenv().get("DATABASE_PASSWORD")
}