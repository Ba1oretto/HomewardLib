package com.baioretto.baiolib.api.extension.player;

import com.baioretto.baiolib.exception.NotActuallyBaioLibException;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

/**
 * The {@code PlayerImpl} used with {@code lombok.experimental.ExtensionMethod}
 *
 * @author baioretto
 * @since 1.1.0
 */
public class PlayerImpl {
    /**
     * Send {@link Player} a {@link Component} message
     * @param in the target player
     * @param message the message
     */
    public static void sendMessage(Player in, Component message) {
        throw new NotActuallyBaioLibException();
    }
}
