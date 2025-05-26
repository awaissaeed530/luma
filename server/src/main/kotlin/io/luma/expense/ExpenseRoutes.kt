package io.luma.expense

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

fun Route.expenseRoutes() {
    val expenseRepository = ExpenseRepository()

    route("expense") {
        get {
            val expenses = transaction {
                 expenseRepository.findAll()
            }

            call.respond(HttpStatusCode.OK, expenses)
        }

        get(":{id}") {
            val id = call.pathParameters.get("id")
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            val expense = expenseRepository.findById(UUID.fromString(id))
            if (expense == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }

            call.respond(HttpStatusCode.OK, expense)
        }

        post {
            val body = call.receive<Expense>()

            val expense = expenseRepository.save {
                title = body.title
                date = body.date
                amount = body.amount
                type = body.type
            }

            call.respond(HttpStatusCode.Created, expense)
        }

        put("{id}") {
            val id = call.pathParameters.get("id")
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }

            val body = call.receive<Expense>()

            if (UUID.fromString(id) != body.id) {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }

            val expense = expenseRepository.findById(UUID.fromString(id))
            if (expense == null) {
                call.respond(HttpStatusCode.NotFound)
                return@put
            }

            expenseRepository.update(UUID.fromString(id)) {
                title = body.title
                date = body.date
                amount = body.amount
                type = body.type
            }

            call.respond(HttpStatusCode.NoContent)
        }
    }
}