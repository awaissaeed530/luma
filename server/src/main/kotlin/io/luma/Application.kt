package io.luma

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.luma.plugins.configureContentNegotiation
import io.luma.plugins.configureDatabase
import io.luma.plugins.configureRouting

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureDatabase()
    configureRouting()
    configureContentNegotiation()
}