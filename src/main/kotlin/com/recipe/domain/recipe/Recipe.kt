package com.recipe.domain.recipe

import com.recipe.handler.routes.models.RecipeRequest
import java.util.*

data class Recipe(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val instructions: String,
    val ingredients: List<String>,
    var isDeleted: Boolean = false,
) {
    fun deleteRecipe() {
        isDeleted = true
    }
}

fun Recipe.toResponse() = RecipeRequest(
    title = this.title,
    instructions = this.instructions,
    ingredients = this.ingredients
)