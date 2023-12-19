package com.recipe.domain.recipe

import java.util.*

interface RecipePersistence {
    fun write(recipe: Recipe): UUID
    fun findById(id: UUID): Recipe?
    fun findAll(): List<Recipe>
}
