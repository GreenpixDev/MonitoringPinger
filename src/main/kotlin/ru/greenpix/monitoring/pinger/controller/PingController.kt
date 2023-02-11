package ru.greenpix.monitoring.pinger.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.greenpix.monitoring.pinger.dto.ServerStatusDto
import ru.greenpix.monitoring.pinger.service.MessageService
import ru.greenpix.monitoring.pinger.service.PingService
import java.net.ConnectException
import java.net.SocketException

@RestController
class PingController(
    private val pingService: PingService,
    private val messageService: MessageService
) {

    @Operation(summary = "Аутентифицировать нового пользователя")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "504", description = "Сервер не отвечает", content = [Content()])
    @GetMapping("ping")
    suspend fun ping(
        @RequestParam("address") address: String
    ): ResponseEntity<ServerStatusDto> {
        return try {
            pingService.ping(address)?.let {
                if (it.favicon != null && !it.favicon.startsWith("data:image/png;base64,")) {
                    ResponseEntity.status(HttpStatus.BAD_GATEWAY).build()
                }
                else ResponseEntity.ok(ServerStatusDto(
                    version = ServerStatusDto.Version(
                        name = it.version.name,
                        protocol = it.version.protocol
                    ),
                    players = ServerStatusDto.Players(
                        max = it.players.max,
                        online = it.players.online
                    ),
                    description = messageService.parse(it.description),
                    favicon = it.favicon
                ))
            } ?: ResponseEntity.status(HttpStatus.BAD_GATEWAY).build()
        }
        catch (e: SocketException) {
            ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).build()
        }
    }
}