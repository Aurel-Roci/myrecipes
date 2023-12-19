package com.recipe.domain.recipe

import com.recipe.infrastructure.db
import java.util.*

class RecipeService() {
    fun saveRecipe(recipe: Recipe): UUID {
        return db.write(recipe)
    }

    fun getById(recipeId: UUID): Recipe? {
        return db.findById(recipeId)
    }

    fun getAll(): List<Recipe> {
        return db.findAll()
    }
}
