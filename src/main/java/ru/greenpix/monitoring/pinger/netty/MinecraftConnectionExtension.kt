package ru.greenpix.monitoring.pinger.netty

import reactor.netty.Connection
import ru.greenpix.monitoring.pinger.join
import ru.greenpix.monitoring.pinger.protocol.ProtocolType

var Connection.state: ProtocolType
    get() = channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get()
    set(value) = channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).set(value)

suspend fun Connection.close() {
    channel().close()
    onDispose().join()
}