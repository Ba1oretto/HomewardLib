package com.baioretto.baiolib.dev.network;

import com.baioretto.baiolib.BaioLibConfig;
import com.baioretto.baiolib.dev.KeepAliveUpdater;
import com.baioretto.baiolib.exception.BaioLibInternalException;
import com.baioretto.baiolib.util.ServerUtil;
import io.netty.channel.*;
import lombok.experimental.UtilityClass;
import net.minecraft.network.protocol.game.ServerboundKeepAlivePacket;
import org.bukkit.entity.Player;

/**
 * Handle player server duplex.
 *
 * @author baioretto
 * @since 1.5.0
 */
@UtilityClass
public class PlayerDuplexAdaptor {
    public void register(Player player) {
        ServerUtil.getChannel(player).pipeline().addBefore("packet_handler", BaioLibConfig.CHANNEL_HANDLER_NAME, new ChannelDuplexHandler() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
                try {
                    super.write(ctx, msg, promise);
                } catch (Exception e) {
                    throw new BaioLibInternalException(e);
                }
            }

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                if (msg instanceof ServerboundKeepAlivePacket && KeepAliveUpdater.isDebugging()) {
                    return;
                }

                try {
                    super.channelRead(ctx, msg);
                } catch (Exception e) {
                    throw new BaioLibInternalException(e);
                }
            }
        });
    }

    public void unregister(Player player) {
        Channel channel = ServerUtil.getChannel(player);
        channel.eventLoop().submit(() -> {
            ChannelPipeline pipeline = channel.pipeline();
            if (pipeline.get(BaioLibConfig.CHANNEL_HANDLER_NAME) == null) return;
            pipeline.remove(BaioLibConfig.CHANNEL_HANDLER_NAME);
        });
    }
}
