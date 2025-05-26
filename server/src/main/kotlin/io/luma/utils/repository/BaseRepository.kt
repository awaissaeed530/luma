package io.luma.utils.repository

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.*
import java.util.UUID

abstract class BaseRepository<T : Entity<UUID>>(
    private val entityClass: EntityClass<UUID, T>
) {
    fun findAll(): List<T> = entityClass.all().toList()

    fun find(filter: SqlExpressionBuilder.() -> Op<Boolean>): List<T> {
        return entityClass.find(SqlExpressionBuilder.filter()).toList()
    }

    fun findById(id: UUID): T? = entityClass.findById(id)

    open fun save(data: T.() -> Unit): T = entityClass.new(data)

    open fun update(id: UUID, data: T.() -> Unit): T? {
        val entity = entityClass.findById(id) ?: return null
        entity.apply(data)
        return entity
    }

    fun deleteById(id: UUID): Boolean {
        return entityClass.findById(id)?.run {
            delete()
            true
        } ?: false
    }
}
