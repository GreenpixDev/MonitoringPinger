package ru.greenpix.monitoring.pinger.protocol.packet

import ru.greenpix.monitoring.pinger.protocol.stream.PacketInputStream
import kotlin.properties.Delegates

class PacketPingResponseOut : PacketOut {

    var payload by Delegates.notNull<Long>()
        private set

    override fun read(packet: PacketInputStream) {
        payload = packet.readLong()
    }

}