package ru.greenpix.monitoring.pinger.protocol.packet

import ru.greenpix.monitoring.pinger.protocol.stream.PacketInputStream

class PacketStatusResponseOut : PacketOut {

    lateinit var response: String
        private set

    override fun read(packet: PacketInputStream) {
        response = packet.readString()
    }

}