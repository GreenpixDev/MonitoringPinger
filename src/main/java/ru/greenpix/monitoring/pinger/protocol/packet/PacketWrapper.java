package ru.greenpix.monitoring.pinger.protocol.packet;

import org.jetbrains.annotations.NotNull;
import ru.greenpix.monitoring.pinger.protocol.stream.PacketInputStream;
import ru.greenpix.monitoring.pinger.protocol.stream.PacketOutputStream;

import java.io.IOException;

/**
 * @author Greenpix
 */

public final class PacketWrapper implements Packet {

    private final int id;

    private byte[] array;

    public PacketWrapper(int id) {
        this.id = id;
    }

    @Override
    public void write(@NotNull PacketOutputStream packet) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void read(@NotNull PacketInputStream packet) throws IOException {
        int len = packet.available();
        array = new byte[len];
        packet.read(array);
    }

    public int getId() {
        return id;
    }

    public byte[] getArray() {
        return array;
    }

    public void setArray(byte[] array) {
        this.array = array;
    }
}
