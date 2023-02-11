package ru.greenpix.monitoring.pinger.protocol.stream;

import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Greenpix
 */

public interface PacketOutputStream extends DataOutput {

    void writeVarInt(int value) throws IOException;

    void writeString(String string) throws IOException;

    @Deprecated
    @Override
    void writeBytes(String s) throws IOException;

    @Deprecated
    @Override
    void writeChars(String s) throws IOException;

    @Deprecated
    @Override
    void writeUTF(String s) throws IOException;
}
