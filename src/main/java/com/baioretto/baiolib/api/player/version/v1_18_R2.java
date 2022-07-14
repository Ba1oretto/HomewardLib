package com.baioretto.baiolib.api.player.version;

import com.baioretto.baiolib.api.Pool;
import com.baioretto.baiolib.api.packet.PacketUtil;
import com.baioretto.baiolib.api.player.IPlayerUtil;
import com.baioretto.baiolib.util.ComponentSerializer;
import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.function.Predicate;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class v1_18_R2 implements IPlayerUtil {
    @Override
    public void send(Player player, Packet<?> packet) {
        ((CraftPlayer) player).getHandle().connection.send(packet);
    }

    @Override
    public <M extends Player> void sendAll(Stream<M> playerStream, Predicate<M> predicate, Packet<?> packet) {
        playerStream.filter(predicate).forEach(player -> ((CraftPlayer) player).getHandle().connection.send(packet));
    }

    @Override
    public void sendMessage(Player in, Component... messages) {
        ServerPlayer player = ((CraftPlayer) in).getHandle();
        if (player.isSleeping()) return;

        ServerGamePacketListenerImpl playerConnection = player.connection;
        for (Component message : messages)
            playerConnection.send(Pool.get(PacketUtil.class).impl().chatPacket(ComponentSerializer.serializeToMinecraft(message)));
    }

    @Override
    public void sendMessage(Player in, String... messages) {
        ServerPlayer player = ((CraftPlayer) in).getHandle();
        if (player.isSleeping()) return;

        ServerGamePacketListenerImpl playerConnection = player.connection;
        for (String message : messages)
            playerConnection.send(Pool.get(PacketUtil.class).impl().chatPacket(ComponentSerializer.serializeToMinecraft(message)));
    }
}
