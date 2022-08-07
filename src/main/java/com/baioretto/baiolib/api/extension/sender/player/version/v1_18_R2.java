package com.baioretto.baiolib.api.extension.sender.player.version;

import com.baioretto.baiolib.api.Pool;
import com.baioretto.baiolib.api.extension.sender.player.IPlayer;
import com.baioretto.baiolib.api.packet.PacketUtil;
import com.baioretto.baiolib.util.ComponentSerializer;
import net.kyori.adventure.text.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;


public class v1_18_R2 implements IPlayer {
    @Override
    public void sendMessage(Player in, Component... messages) {
        ServerPlayer player = ((CraftPlayer) in).getHandle();
        if (player.isSleeping()) return;

        ServerGamePacketListenerImpl playerConnection = player.connection;
        for (Component message : messages)
            playerConnection.send(Pool.get(PacketUtil.class).impl().chatPacket(ComponentSerializer.serializeToMinecraft(message)));
    }
}
