package com.recipe.infrastructure

import com.recipe.ConfigProvider
import com.recipe.infrastructure.entities.Recipes
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseSingleton {
    fun init(config: ConfigProvider.Persistence) {
        println(config)
        val database = Database.connect(
            url = config.url,
            driver = "org.postgresql.Driver",
            user = config.user,
            password = config.password
        )
        transaction(database) {
            SchemaUtils.create(Recipes)
        }
    }
}
