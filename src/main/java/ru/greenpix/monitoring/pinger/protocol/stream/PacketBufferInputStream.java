package ru.greenpix.monitoring.pinger.protocol.stream;

import ru.greenpix.monitoring.pinger.netty.PacketBuffer;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Greenpix
 */

public class PacketBufferInputStream implements PacketInputStream {

    private final PacketBuffer buffer;

    public PacketBufferInputStream(PacketBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public int available() throws IOException {
        return buffer.readableBytes();
    }

    @Override
    public int read() throws IOException {
        return readByte();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        buffer.readBytes(b, off, len);
        return len;
    }

    @Override
    public String readString() throws IOException {
        return buffer.readString();
    }

    @Override
    public int readVarInt() throws IOException {
        return buffer.readVarInt();
    }

    @Override
    public void readFully(byte[] b) throws IOException {
        read(b, 0, b.length);
    }

    @Override
    public void readFully(byte[] b, int off, int len) throws IOException {
        read(b, off, len);
    }

    @Override
    public int skipBytes(int n) throws IOException {
        buffer.skipBytes(n);
        return n;
    }

    @Override
    public boolean readBoolean() throws IOException {
        return buffer.readBoolean();
    }

    @Override
    public byte readByte() throws IOException {
        return buffer.readByte();
    }

    @Override
    public int readUnsignedByte() throws IOException {
        return buffer.readUnsignedByte();
    }

    @Override
    public short readShort() throws IOException {
        return buffer.readShort();
    }

    @Override
    public int readUnsignedShort() throws IOException {
        return buffer.readUnsignedShort();
    }

    @Override
    public char readChar() throws IOException {
        return buffer.readChar();
    }

    @Override
    public int readInt() throws IOException {
        return buffer.readInt();
    }

    @Override
    public long readLong() throws IOException {
        return buffer.readLong();
    }

    @Override
    public float readFloat() throws IOException {
        return buffer.readFloat();
    }

    @Override
    public double readDouble() throws IOException {
        return buffer.readDouble();
    }

    @Deprecated
    @Override
    public String readLine() throws IOException {
        return buffer.readString();
    }

    @Deprecated
    @Override
    public String readUTF() throws IOException {
        return buffer.readString();
    }

    @Override
    public UUID readUuidIntArray() {
        return buffer.readUuidIntArray();
    }
}
