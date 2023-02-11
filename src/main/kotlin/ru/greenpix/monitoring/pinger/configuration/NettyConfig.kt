package ru.greenpix.monitoring.pinger.configuration

import io.netty.channel.ChannelOption
import io.netty.channel.epoll.EpollEventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.netty.tcp.TcpClient
import ru.greenpix.monitoring.pinger.netty.NettyPacketDecoder
import ru.greenpix.monitoring.pinger.netty.NettyPacketEncoder
import ru.greenpix.monitoring.pinger.netty.NettyVarint21FrameDecoder
import ru.greenpix.monitoring.pinger.netty.NettyVarint21FrameEncoder
import ru.greenpix.monitoring.pinger.netty.NetworkManager
import ru.greenpix.monitoring.pinger.settings.NettySettings
import java.util.concurrent.TimeUnit

@Configuration
class NettyConfig(
    private val nettySettings: NettySettings,
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun tcpClient(): TcpClient
    = TcpClient.create()
        .option(ChannelOption.TCP_NODELAY, true)
        .option(ChannelOption.SO_KEEPALIVE, true)
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, nettySettings.connectTimeout)
        .doOnConnected {
            it.addHandlerLast("read_timeout", ReadTimeoutHandler(nettySettings.readTimeout, TimeUnit.MILLISECONDS))
            it.addHandlerLast("write_timeout", WriteTimeoutHandler(nettySettings.writeTimeout, TimeUnit.MILLISECONDS))
        }

}