package ru.greenpix.monitoring.pinger.dto

data class ServerStatusDto(
    val version: Version,
    val players: Players,
    val description: String,
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