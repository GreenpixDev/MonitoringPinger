package ru.greenpix.monitoring.pinger.protocol.packet;

import org.jetbrains.annotations.NotNull;
import ru.greenpix.monitoring.pinger.protocol.stream.PacketInputStream;

import java.io.IOException;

/**
 * @author Greenpix
 */

public interface PacketIn extends Packet {

    @Override
    default void read(@NotNull PacketInputStream packet) throws IOException {
        throw new UnsupportedOperationException();
    }
}
