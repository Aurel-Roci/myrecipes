package com.recipe.infrastructure

import com.recipe.domain.recipe.Recipe
import com.recipe.domain.recipe.RecipePersistence
import com.recipe.infrastructure.entities.Recipes
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class Repository() : RecipePersistence {

    private fun toDomainModel(row: ResultRow) = Recipe(
        id = row[Recipes.id],
        title = row[Recipes.title],
        instructions = row[Recipes.instructions],
        ingredients = row[Recipes.ingredients].split(","),
        isDeleted = row[Recipes.isDeleted]
    )

    override fun write(recipe: Recipe): UUID {
        return transaction {
            Recipes.insert {
                it[this.id] = recipe.id
                it[this.title] = recipe.title
                it[this.instructions] = recipe.instructions
                it[this.ingredients] = recipe.ingredients.joinToString(",")
                it[this.createdAt] = Clock.System.now()
                it[this.updatedAt] = Clock.System.now()
                it[this.isDeleted] = false
            } get Recipes.id
        }
    }

    override fun findById(id: UUID): Recipe? {
        return transaction {
            Recipes
                .select { Recipes.id eq id }
                .map { toDomainModel(it) }
                .singleOrNull()
        }
    }

    override fun findAll(): List<Recipe> {
        return transaction {
            Recipes
                .selectAll()
                .map { toDomainModel(it) }
        }
    }
}

val db: RecipePersistence = Repository()
