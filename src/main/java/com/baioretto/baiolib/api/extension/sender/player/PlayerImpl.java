package com.baioretto.baiolib.api.extension.sender.player;

import com.baioretto.baiolib.api.Pool;
import com.baioretto.baiolib.api.player.PlayerUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

/**
 * The {@code PlayerImpl} used with {@link lombok.experimental.ExtensionMethod}
 *
 * @author baioretto
 * @since 1.1.0
 */
@SuppressWarnings("unused")
public class PlayerImpl {
    /**
     * Send {@link Player} a {@link Component} message
     * @param in the target player
     * @param message the message
     */
    public static void sendMessage(Player in, Component message) {
        Pool.get(PaperPlayer.class).impl().sendMessage(in, message);
    }
}
