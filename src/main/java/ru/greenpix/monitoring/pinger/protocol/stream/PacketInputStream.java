package ru.greenpix.monitoring.pinger.protocol.stream;

import java.io.DataInput;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Greenpix
 */

public interface PacketInputStream extends DataInput {

    int available() throws IOException;

    int read() throws IOException;

    int read(byte b[]) throws IOException;

    int read(byte b[], int off, int len) throws IOException;

    String readString() throws IOException;

    int readVarInt() throws IOException;

    @Deprecated
    @Override
    String readLine() throws IOException;

    @Deprecated
    @Override
    String readUTF() throws IOException;

    UUID readUuidIntArray();
}
