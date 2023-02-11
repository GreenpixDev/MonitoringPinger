package ru.greenpix.monitoring.pinger.netty;

import io.netty.channel.*;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalServerChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.greenpix.monitoring.pinger.protocol.MinecraftConnection;
import ru.greenpix.monitoring.pinger.protocol.ProtocolType;
import ru.greenpix.monitoring.pinger.protocol.packet.Packet;

import java.net.ConnectException;
import java.net.SocketAddress;
import java.util.concurrent.TimeoutException;

/**
 * @author Greenpix
 */

public class NetworkManager extends SimpleChannelInboundHandler<Packet> {

    public static final AttributeKey<ProtocolType> PROTOCOL_ATTRIBUTE_KEY = AttributeKey.valueOf("protocol");

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Channel channel;

    private boolean closed = false;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (logger.isTraceEnabled() && !(cause instanceof ConnectException) && !(cause instanceof TimeoutException)) {
            logger.error("An occurred netty exception", cause);
        }
        closeChannel();
    }

    @Override
    public void channelInactive(@NotNull ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        closeChannel();
    }

    @Override
    public void channelActive(@NotNull ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        channel = ctx.channel();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) {
        if (channel.isOpen()) {
            try {

            } catch (Exception e) {
                logger.warn("An occurred exception on receive packet", e);
            }
        }
    }

    public void sendPacket(Packet packetIn) {
        sendPacket(packetIn, null);
    }

    public void sendPacket(Packet packetIn, GenericFutureListener<? extends Future<? super Void>> futureListeners) {
        if (channel.eventLoop().inEventLoop()) {
            ChannelFuture channelfuture = channel.writeAndFlush(packetIn);
            if (futureListeners != null) {
                channelfuture.addListener(futureListeners);
            }
            channelfuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        } else {
            channel.eventLoop().execute(() -> {
                ChannelFuture channelfuture1 = channel.writeAndFlush(packetIn);
                if (futureListeners != null) {
                    channelfuture1.addListener(futureListeners);
                }
                channelfuture1.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
            });
        }
    }

    public void setConnectionState(ProtocolType newState) {
        channel.attr(PROTOCOL_ATTRIBUTE_KEY).set(newState);
        channel.config().setAutoRead(true);
        //log.debug("Enabled auto read");
    }

    public void setCompressionThreshold(int threshold) {
        if (threshold >= 0) {
            if (channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
                ((NettyCompressionDecoder) channel.pipeline().get("decompress")).setCompressionThreshold(threshold);
            } else {
                channel.pipeline().addBefore("decoder", "decompress", new NettyCompressionDecoder(threshold));
            }

            if (channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
                ((NettyCompressionEncoder) channel.pipeline().get("compress")).setCompressionThreshold(threshold);
            } else {
                channel.pipeline().addBefore("encoder", "compress", new NettyCompressionEncoder(threshold));
            }
        } else {
            if (channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
                channel.pipeline().remove("decompress");
            }

            if (channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
                channel.pipeline().remove("compress");
            }
        }
    }

    public void closeChannel() {
        if (!closed) {
            if (channel.isOpen()) {
                channel.close();
            }
            closed = true;
        }
    }

    public boolean isChannelOpen() {
        return channel != null && channel.isOpen();
    }

    public boolean isLocalChannel() {
        return channel instanceof LocalChannel || channel instanceof LocalServerChannel;
    }

    public SocketAddress getRemoteAddress() {
        return channel != null ? channel.remoteAddress() : null;
    }
}
