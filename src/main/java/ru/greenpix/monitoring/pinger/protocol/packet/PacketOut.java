package ru.greenpix.monitoring.pinger.protocol.packet;

import org.jetbrains.annotations.NotNull;
import ru.greenpix.monitoring.pinger.protocol.stream.PacketOutputStream;

import java.io.IOException;

/**
 * @author Greenpix
 */

public interface PacketOut extends Packet {

    @Override
    default void write(@NotNull PacketOutputStream packet) throws IOException {
        throw new UnsupportedOperationException();
    }
}
