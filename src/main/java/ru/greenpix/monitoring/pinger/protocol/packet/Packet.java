package ru.greenpix.monitoring.pinger.protocol.packet;

import org.jetbrains.annotations.NotNull;
import ru.greenpix.monitoring.pinger.protocol.stream.PacketInputStream;
import ru.greenpix.monitoring.pinger.protocol.stream.PacketOutputStream;

import java.io.IOException;

/**
 * @author Greenpix
 */

public interface Packet {

    void write(@NotNull PacketOutputStream packet) throws IOException;

    void read(@NotNull PacketInputStream packet) throws IOException;

}
