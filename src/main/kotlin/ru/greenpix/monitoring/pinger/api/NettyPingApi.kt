package ru.greenpix.monitoring.pinger.api

import io.netty.channel.ChannelHandlerContext
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Component
import reactor.netty.tcp.TcpClient
import ru.greenpix.monitoring.pinger.join
import ru.greenpix.monitoring.pinger.netty.*
import ru.greenpix.monitoring.pinger.protocol.ProtocolType
import ru.greenpix.monitoring.pinger.protocol.packet.*

@Component
class NettyPingApi(
    private val tcpClient: TcpClient
) : PingApi {

    override suspend fun pingServer(host: String, port: Int): PacketStatusResponseOut?
    = tcpClient
        .doOnChannelInit { observer, channel, remoteAddress ->
            channel.pipeline()
                .addLast("prepender", NettyVarint21FrameEncoder())
                .addLast("encoder", NettyPacketEncoder())
        }
        .doOnConnected {
            it.addHandlerLast("splitter", NettyVarint21FrameDecoder())
            it.addHandlerLast("decoder", NettyPacketDecoder())
        }
        .host(host)
        .port(port)
        .connect()
        .awaitSingle()
        .let {
            it.state = ProtocolType.HANDSHAKING
            it.outbound()
                .sendObject(PacketHandshakeIn(host, port))
                .then()
                .join()

            it.state = ProtocolType.STATUS
            it.outbound()
                .sendObject(PacketStatusResponseIn())
                .then()
                .subscribe()

            val message = it.inbound()
                .receiveObject()
                .awaitFirstOrNull()
            it.close()

            if (message is PacketStatusResponseOut) message
            else null
        }

}