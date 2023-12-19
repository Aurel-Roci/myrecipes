package com.recipe

import com.recipe.handler.routes.deleteRecipe
import com.recipe.handler.routes.getRecipe
import com.recipe.handler.routes.getRecipes
import com.recipe.handler.routes.models.recipeValidation
import com.recipe.handler.routes.postRecipe
import com.recipe.infrastructure.DatabaseSingleton
import io.github.cdimascio.dotenv.dotenv
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun main(args: Array<String>) {
    val env = dotenv()
    System.setProperty("DB_URL", env["DB_URL"])
    System.setProperty("DB_USER", env["DB_USER"])
    System.setProperty("DB_PASS", env["DB_PASS"])
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val config: ConfigProvider = loadConfig(ConfigProvider.serializer())
    DatabaseSingleton.init(config.db)
    configureRouting()
    configureSerialization()
    configureValidation()
    configureStatusPages()
}

fun Application.configureRouting() {
    routing {
        route("/recipe") {
            getRecipe()
            getRecipes()
            postRecipe()
            deleteRecipe()
        }
    }
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}

fun Application.configureValidation() {
    install(RequestValidation) {
        recipeValidation()
    }
}

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.reasons.joinToString())
        }
    }
}
