package ru.greenpix.monitoring.pinger.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.greenpix.monitoring.pinger.protocol.packet.Packet;
import ru.greenpix.monitoring.pinger.protocol.stream.PacketBufferInputStream;

import java.io.IOException;
import java.util.List;

public class NettyPacketDecoder extends ByteToMessageDecoder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    protected void decode(ChannelHandlerContext context, ByteBuf buf, List<Object> list) throws Exception {
        if (buf.readableBytes() != 0) {
            PacketBuffer packetbuffer = new PacketBuffer(buf);
            int id = packetbuffer.readVarInt();
            Packet packet = context.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get().getPacketById(id);
            if (packet == null) {
                throw new IOException("Bad packet id " + id);
            } else {
                packet.read(new PacketBufferInputStream(packetbuffer));
                int readable = packetbuffer.readableBytes();
                if (readable > 0) {
                    //throw new IOException("Packet " + context.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get().name() + "/" + id + " (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + packetbuffer.readableBytes() + " bytes extra whilst reading packet " + id);
                    packetbuffer.skipBytes(readable);
                }
                list.add(packet);
                /*if (log.isDebugEnabled()) {
                    log.debug(" IN: [{}:{}] {}", context.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get(), id, packet.getClass().getName());
                }*/
            }
        }
    }
}
