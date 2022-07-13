package com.baioretto.baiolib.api.player.version;

import com.baioretto.baiolib.api.Pool;
import com.baioretto.baiolib.api.component.ComponentUtil;
import com.baioretto.baiolib.api.component.IComponentUtil;
import com.baioretto.baiolib.api.packet.PacketUtil;
import com.baioretto.baiolib.api.player.IPlayerUtil;
import com.google.gson.JsonParser;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
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
        ServerPlayer serverPlayer = ((CraftPlayer) in).getHandle();
        if (serverPlayer.isSleeping()) return;

        ServerGamePacketListenerImpl connection = serverPlayer.connection;
        IComponentUtil componentUtil = Pool.get(ComponentUtil.class).impl();
        for (Component message : messages)
            connection.send(Pool.get(PacketUtil.class).impl().chatPacket(componentUtil.jsonToMinecraftComponent(componentUtil.decomponent((TextComponent) message))));
    }

    @Override
    public void sendMessage(Player in, String... messages) {
        ServerPlayer serverPlayer = ((CraftPlayer) in).getHandle();
        if (serverPlayer.isSleeping()) return;

        ServerGamePacketListenerImpl connection = serverPlayer.connection;
        IComponentUtil componentUtil = Pool.get(ComponentUtil.class).impl();
        for (String message : messages)
            connection.send(Pool.get(PacketUtil.class).impl().chatPacket(componentUtil.jsonToMinecraftComponent(JsonParser.parseString(message))));
    }
}
