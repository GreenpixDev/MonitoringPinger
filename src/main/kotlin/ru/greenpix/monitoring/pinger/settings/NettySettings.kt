package ru.greenpix.monitoring.pinger.settings

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "netty")
data class NettySettings @ConstructorBinding constructor(
    val connectTimeout: Int,
    val readTimeout: Long,
    val writeTimeout: Long,
    val bufferSize: Int
)
