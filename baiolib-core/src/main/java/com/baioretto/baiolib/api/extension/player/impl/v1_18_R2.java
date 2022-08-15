package com.baioretto.baiolib.api.extension.player.impl;

import com.baioretto.baiolib.api.extension.player.IPlayer;
import com.baioretto.baiolib.util.ComponentSerializer;
import net.kyori.adventure.text.Component;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.protocol.game.PacketPlayOutChat;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class v1_18_R2 implements IPlayer {
    @Override
    public void sendMessage(Player in, Component... messages) {
        EntityPlayer player = ((CraftPlayer) in).getHandle();
        if (player.fc()) return;
        PlayerConnection playerConnection = player.b;

        for (Component message : messages)
            playerConnection.a(new PacketPlayOutChat(ComponentSerializer.serializeToMinecraft(message), ChatMessageType.b, UUID.randomUUID()));
    }
}
