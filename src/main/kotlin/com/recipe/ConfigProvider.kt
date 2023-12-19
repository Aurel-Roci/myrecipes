package com.recipe

import com.typesafe.config.ConfigFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.hocon.Hocon

@Serializable
data class ConfigProvider(
    val db: Persistence
) {
    @Serializable
    data class Persistence(
        val url: String,
        val user: String,
        val password: String
    )
}

fun <Config> loadConfig(serializer: KSerializer<Config>, resourceFileName: String = "application.conf"): Config =
    @OptIn(ExperimentalSerializationApi::class)
    Hocon.decodeFromConfig(serializer, ConfigFactory.load(resourceFileName))
