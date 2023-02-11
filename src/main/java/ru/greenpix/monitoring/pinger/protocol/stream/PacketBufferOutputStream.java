package ru.greenpix.monitoring.pinger.protocol.stream;

import ru.greenpix.monitoring.pinger.netty.PacketBuffer;

import java.io.IOException;

/**
 * @author Greenpix
 */

public class PacketBufferOutputStream implements PacketOutputStream {

    private final PacketBuffer buffer;

    public PacketBufferOutputStream(PacketBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void writeVarInt(int value) throws IOException {
        buffer.writeVarInt(value);
    }

    @Override
    public void writeString(String string) throws IOException {
        buffer.writeString(string);
    }

    @Override
    public void write(int b) throws IOException {
        writeByte(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        buffer.writeBytes(b, off, len);
    }

    @Override
    public void writeBoolean(boolean v) throws IOException {
        buffer.writeBoolean(v);
    }

    @Override
    public void writeByte(int v) throws IOException {
        buffer.writeByte(v);
    }

    @Override
    public void writeShort(int v) throws IOException {
        buffer.writeShort(v);
    }

    @Override
    public void writeChar(int v) throws IOException {
        buffer.writeChar(v);
    }

    @Override
    public void writeInt(int v) throws IOException {
        buffer.writeInt(v);
    }

    @Override
    public void writeLong(long v) throws IOException {
        buffer.writeLong(v);
    }

    @Override
    public void writeFloat(float v) throws IOException {
        buffer.writeFloat(v);
    }

    @Override
    public void writeDouble(double v) throws IOException {
        buffer.writeDouble(v);
    }

    @Deprecated
    @Override
    public void writeBytes(String s) throws IOException {
        buffer.writeString(s);
    }

    @Deprecated
    @Override
    public void writeChars(String s) throws IOException {
        buffer.writeString(s);
    }

    @Deprecated
    @Override
    public void writeUTF(String s) throws IOException {
        buffer.writeString(s);
    }
}
