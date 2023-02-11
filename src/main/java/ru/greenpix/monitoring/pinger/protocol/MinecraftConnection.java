package ru.greenpix.monitoring.pinger.protocol;

import ru.greenpix.monitoring.pinger.protocol.packet.Packet;

import java.io.Closeable;

public interface MinecraftConnection extends Closeable {

    void handshake();

    void receivePacket(Packet msg);

}
