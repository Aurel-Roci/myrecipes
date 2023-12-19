package com.recipe.handler.routes.models

import com.recipe.domain.recipe.Recipe
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import kotlinx.serialization.Serializable

@Serializable
data class RecipeRequest(val title: String, val instructions: String, val ingredients: List<String>)

fun RecipeRequest.toDomainModel() = Recipe(
    title = this.title,
    instructions = this.instructions,
    ingredients = this.ingredients
)

fun RequestValidationConfig.recipeValidation() {
    validate<RecipeRequest> { recipe ->
        if (recipe.title.isBlank()) {
            ValidationResult.Invalid("Title must not be empty")
        } else if (recipe.instructions.isBlank()) {
            ValidationResult.Invalid("Instructions must not be empty")
        } else if (recipe.ingredients.isEmpty()) {
            ValidationResult.Invalid("Ingredients must not be empty")
        } else {
            // Everything is ok!
            ValidationResult.Valid
        }
    }
}
