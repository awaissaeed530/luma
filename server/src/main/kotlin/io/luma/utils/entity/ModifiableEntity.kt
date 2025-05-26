package io.luma.utils.entity

import kotlinx.datetime.*
import org.jetbrains.exposed.dao.EntityChangeType
import org.jetbrains.exposed.dao.EntityHook
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.dao.toEntity
import org.jetbrains.exposed.sql.kotlin.datetime.*
import java.util.UUID

open class ModifiableTable(name: String) : AuditableTable(name) {
    val updatedAt = timestamp("updated_at").nullable()
}

abstract class ModifiableEntity(id: EntityID<UUID>, table: ModifiableTable) : AuditableEntity(id, table) {
    var updatedAt by table.updatedAt
}

abstract class ModifiableEntityClass<E : ModifiableEntity>(table: BaseTable) : AuditableEntityClass<E>(table) {
    init {
        EntityHook.subscribe { action ->
            if (action.changeType == EntityChangeType.Updated) {
                action.toEntity(this)?.updatedAt = Clock.System.now()
            }
        }
    }
}