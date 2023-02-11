package ru.greenpix.monitoring.pinger.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.greenpix.monitoring.pinger.protocol.ProtocolType;
import ru.greenpix.monitoring.pinger.protocol.packet.Packet;
import ru.greenpix.monitoring.pinger.protocol.stream.PacketBufferOutputStream;

import java.io.IOException;

public class NettyPacketEncoder extends MessageToByteEncoder<Packet> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    protected void encode(ChannelHandlerContext context, Packet packet, ByteBuf buf) throws Exception {
        ProtocolType protocolType = context.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get();
        if (protocolType == null) {
            throw new RuntimeException("ConnectionProtocol unknown: " + packet);
        } else {
            Integer integer = protocolType.getIdByPacket(packet);
            /*if (log.isDebugEnabled()) {
                log.debug("OUT: [{}:{}] {}", context.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get(), integer, packet.getClass().getName());
            }*/
            if (integer == null) {
                throw new IOException("Can't serialize unregistered packet");
            } else {
                PacketBuffer packetbuffer = new PacketBuffer(buf);
                packetbuffer.writeVarInt(integer);

                try {
                    packet.write(new PacketBufferOutputStream(packetbuffer));
                } catch (Throwable throwable) {
                    //logger.error(throwable);
                    /*if (packet.shouldSkipErrors()) {
                        throw new SkipableEncoderException(throwable);
                    } else {
                        throw throwable;
                    }*/
                    throw throwable;
                }
            }
        }
    }
}
