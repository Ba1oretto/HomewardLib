package com.baioretto.baiolib.api.extension.player;

import com.baioretto.baiolib.annotation.Instantiable;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

/**
 * The {@code IPlayer} class provides player bound method.
 *
 * @author baioretto
 * @since 1.1.0
 */
@Instantiable
public interface IPlayer {
    /**
     * Send {@link Player} a {@link Component} message
     * @param in the target player
     * @param messages the message
     */
    void sendMessage(Player in, Component... messages);
}
