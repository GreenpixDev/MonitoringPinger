package ru.greenpix.monitoring.pinger.api

import ru.greenpix.monitoring.pinger.protocol.packet.PacketStatusResponseOut

interface PingApi {

    suspend fun pingServer(host: String, port: Int): PacketStatusResponseOut?

}