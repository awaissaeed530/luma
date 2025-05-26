package io.luma.utils.entity

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*
import java.util.UUID

open class BaseTable(name: String) : UUIDTable(name)

abstract class BaseEntity(id: EntityID<UUID>) : UUIDEntity(id)

abstract class BaseEntityClass<E: BaseEntity>(table: UUIDTable) : UUIDEntityClass<E>(table)
