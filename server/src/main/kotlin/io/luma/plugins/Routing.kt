package io.luma.plugins

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import io.luma.expense.expenseRoutes

fun Application.configureRouting() {
    routing {
        expenseRoutes()
    }
}