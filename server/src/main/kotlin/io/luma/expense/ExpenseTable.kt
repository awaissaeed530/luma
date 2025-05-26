package io.luma.expense

import io.luma.utils.entity.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.sql.kotlin.datetime.date
import java.util.UUID

object ExpenseTypeTable : ModifiableTable("expense_types") {
    val name = varchar("name", length = 30)
}

@Serializable
class ExpenseType(id: EntityID<UUID>) : ModifiableEntity(id, ExpenseTypeTable) {
    companion object : ModifiableEntityClass<ExpenseType>(ExpenseTypeTable)

    var name by ExpenseTypeTable.name
    val expenses by Expense referrersOn ExpenseTable.type
}

object ExpenseTable : ModifiableTable("expenses") {
    val title = varchar("title", length = 30)
    val date = date("date")
    val amount = decimal("amount", precision = 10, scale = 2)
    val type = reference("type_id", ExpenseTypeTable)
}

@Serializable
class Expense(id: EntityID<UUID>) : ModifiableEntity(id, ExpenseTable) {
    companion object : ModifiableEntityClass<Expense>(ExpenseTable)

    var title by ExpenseTable.title
    var date by ExpenseTable.date
    var amount by ExpenseTable.amount
    var type by ExpenseType referencedOn ExpenseTable.type
}
