package com.recipe.handler.routes

import com.recipe.domain.recipe.RecipeService
import com.recipe.domain.recipe.toResponse
import com.recipe.handler.routes.models.RecipeRequest
import com.recipe.handler.routes.models.toDomainModel
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.util.getOrFail
import java.util.*

fun Route.getRecipe() {
    get("/{id}") {
        val id = call.parameters.getOrFail<UUID>("id")
        val recipe = RecipeService().getById(id)
        if (recipe != null) {
            call.respond(HttpStatusCode.OK, recipe.toResponse())
        } else {
            call.respondText("No recipe found", status = HttpStatusCode.NotFound)
        }
    }
}

fun Route.getRecipes() {
    get {
        val recipes = RecipeService().getAll()
        if (recipes.isNotEmpty()) {
            call.respond(HttpStatusCode.OK, recipes.map { it.toResponse() })
        } else {
            call.respondText("No recipes found", status = HttpStatusCode.NotFound)
        }
    }
}

fun Route.postRecipe() {
    post {
        val recipe = call.receive<RecipeRequest>()
        val recipeId = RecipeService().saveRecipe(recipe.toDomainModel())
        call.respondText("Recipe stored with $recipeId", status = HttpStatusCode.Created)
    }
}

fun Route.deleteRecipe() {
    delete("{id?}") {
        val id = call.parameters["id"] ?: return@delete call.respondText(
            "Missing id",
            status = HttpStatusCode
                .BadRequest
        )
        println("Recipe deleted $id")
    }
}
