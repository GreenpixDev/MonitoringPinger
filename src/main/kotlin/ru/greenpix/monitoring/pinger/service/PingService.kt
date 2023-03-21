package ru.greenpix.monitoring.pinger.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import ru.greenpix.monitoring.pinger.api.PingApi
import ru.greenpix.monitoring.pinger.model.ServerStatus

@Service
class PingService(
    private val pingApi: PingApi,
    private val objectMapper: ObjectMapper
) {

    private val errorVersions = setOf("§4● Offline", "⚠ Error")

    suspend fun ping(address: String): ServerStatus? {
        val addressSeparated = address.split(':')
        val host = addressSeparated[0]
        val port = if (addressSeparated.size > 1) addressSeparated[1].toInt() else 25565

        val packet = pingApi.pingServer(host, port)

        return if (packet != null) {
            val status = objectMapper.readValue(packet.response, ServerStatus::class.java)
            if (status.version.name !in errorVersions) {
                status
            }
            else null
        }
        else null
    }
}