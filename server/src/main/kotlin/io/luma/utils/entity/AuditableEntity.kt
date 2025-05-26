package io.luma.utils.entity

import kotlinx.datetime.*
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import java.util.UUID

open class AuditableTable(name: String) : BaseTable(name) {
    val createdAt = timestamp("created_at").clientDefault { Clock.System.now() }
}

abstract class AuditableEntity(id: EntityID<UUID>, table: AuditableTable) : BaseEntity(id) {
    val createdAt by table.createdAt
}

abstract class AuditableEntityClass<E: AuditableEntity>(table: BaseTable) : BaseEntityClass<E>(table)
