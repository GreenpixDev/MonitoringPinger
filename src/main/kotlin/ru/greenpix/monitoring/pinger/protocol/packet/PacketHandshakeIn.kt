package ru.greenpix.monitoring.pinger.protocol.packet

import ru.greenpix.monitoring.pinger.protocol.stream.PacketOutputStream

class PacketHandshakeIn(
    private val host: String,
    private val port: Int
) : PacketIn {

    override fun write(packet: PacketOutputStream) {
        packet.writeVarInt(754)
        packet.writeString(host)
        packet.writeShort(port)
        packet.writeVarInt(		1)
    }
}