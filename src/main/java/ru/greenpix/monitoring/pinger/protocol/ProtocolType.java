package ru.greenpix.monitoring.pinger.protocol;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import ru.greenpix.monitoring.pinger.protocol.packet.Packet;
import ru.greenpix.monitoring.pinger.protocol.packet.PacketHandshakeIn;
import ru.greenpix.monitoring.pinger.protocol.packet.PacketPingResponseIn;
import ru.greenpix.monitoring.pinger.protocol.packet.PacketPingResponseOut;
import ru.greenpix.monitoring.pinger.protocol.packet.PacketStatusResponseIn;
import ru.greenpix.monitoring.pinger.protocol.packet.PacketStatusResponseOut;
import ru.greenpix.monitoring.pinger.protocol.packet.PacketWrapper;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * @author Greenpix
 */

public enum ProtocolType {

    HANDSHAKING,
    STATUS,
    LOGIN,
    PLAY,

    ;

    static {
        HANDSHAKING.registerPacketIn(0x00, PacketHandshakeIn.class);
        STATUS.registerPacketIn(0x00, PacketStatusResponseIn.class);
        STATUS.registerPacketIn(0x01, PacketPingResponseIn.class);
        STATUS.registerPacketOut(0x00, PacketStatusResponseOut::new);
        STATUS.registerPacketOut(0x01, PacketPingResponseOut::new);
    }

    private final Int2ObjectMap<Supplier<Packet>> out = new Int2ObjectOpenHashMap<>();

    private final Object2IntMap<Class<? extends Packet>> in = new Object2IntOpenHashMap<>();

    public void registerPacketOut(int id, Supplier<Packet> constructor) {
        out.put(id, constructor);
    }

    public void registerPacketIn(int id, Class<? extends Packet> cls) {
        in.put(cls, id);
    }

    public int getIdByPacketType(Class<?> cls) {
        return in.getInt(cls);
    }

    public Packet getPacketById(int id) {
        Supplier<Packet> constructor = out.get(id);
        return constructor == null ? new PacketWrapper(id) : constructor.get();
    }

    public int getIdByPacket(Packet packet) {
        return in.getInt(packet.getClass());
    }

}
