package com.baioretto.baiolib.api.player.impl;

import com.baioretto.baiolib.api.player.IPlayerUtil;
import io.netty.channel.Channel;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.function.Predicate;
import java.util.stream.Stream;


public class v1_18_R2 implements IPlayerUtil {
    @Override
    public void send(Player player, Packet<?> packet) {
        this.getConnection(player).a(packet);
    }

    @Override
    public <M extends Player> void sendAll(Stream<M> playerStream, Predicate<M> predicate, Packet<?> packet) {
        playerStream.filter(predicate).forEach(player -> this.getConnection(player).a(packet));
    }

    @Override
    public PlayerConnection getConnection(Player player) {
        return ((CraftPlayer) player).getHandle().b;
    }

    @Override
    public NetworkManager getNetworkManager(Player player) {
        return this.getConnection(player).a;
    }

    @Override
    public Channel getChannel(Player player) {
        return this.getNetworkManager(player).m;
    }
}
