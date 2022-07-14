package com.baioretto.baiolib.api.player.version;

import com.baioretto.baiolib.api.player.IPlayerUtil;
import net.minecraft.network.protocol.Packet;
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
}
