package ru.greenpix.monitoring.pinger.service

import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import ru.greenpix.monitoring.pinger.api.PingApi
import ru.greenpix.monitoring.pinger.model.ServerStatus

@Service
class PingService(
    private val pingApi: PingApi,
    private val objectMapper: ObjectMapper
) {

    suspend fun ping(address: String): ServerStatus? {
        val addressSeparated = address.split(':')
        val host = addressSeparated[0]
        var port = if (addressSeparated.size > 1) addressSeparated[1].toInt() else 25565

        var packet = pingApi.pingServer(host, port)

        return if (packet != null) {
            objectMapper.readValue(packet.response, ServerStatus::class.java)
        }
        else null
    }
}