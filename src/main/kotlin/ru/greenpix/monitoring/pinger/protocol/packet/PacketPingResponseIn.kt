package ru.greenpix.monitoring.pinger.protocol.packet

import ru.greenpix.monitoring.pinger.protocol.stream.PacketOutputStream

class PacketPingResponseIn(
    private val payload: Long
) : PacketIn {

    override fun write(packet: PacketOutputStream) {
        packet.writeLong(payload)
    }

}