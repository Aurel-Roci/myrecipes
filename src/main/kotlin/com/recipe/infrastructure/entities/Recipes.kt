package com.recipe.infrastructure.entities

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object Recipes : Table() {
    val id = uuid("id")
    val title = varchar("title", 128)
    val instructions = varchar("instructions", 1024)
    val ingredients = varchar("ingredients", 1024) // TODO: Change to list of strings
    val createdAt = timestamp("createdAt")
    val updatedAt = timestamp("updatedAt")
    val isDeleted = bool("isDeleted")

    override val primaryKey = PrimaryKey(id)
}
