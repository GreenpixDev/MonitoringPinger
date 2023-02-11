package ru.greenpix.monitoring.pinger.model

import com.fasterxml.jackson.databind.JsonNode

data class ServerStatus(
    val version: Version,
    val players: Players,
    val description: JsonNode,
    val favicon: String?
) {
    data class Version(
        val name: String,
        val protocol: Int
    )

    data class Players(
        val max: Int,
        val online: Int
    )
}