package io.luma.plugins

import io.ktor.server.application.Application
import io.luma.config.DatabaseFactory

fun Application.configureDatabase() {
    DatabaseFactory.init()
}