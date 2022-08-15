package com.baioretto.baiolib.api.extension.console;

import com.baioretto.baiolib.api.CachedVersionWrapper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.command.ConsoleCommandSender;

/**
 * The {@link ConsoleCommandSender} implementation.
 *
 * @author baioretto
 * @since 1.1.0
 */
public class ConsoleCommandSenderImpl {
    /**
     * Sends a chat message.
     *
     * @param message a message
     * @see Component
     * @since 4.1.0
     */
    public static void sendMessage(ConsoleCommandSender in, Component message) {
        CachedVersionWrapper.of(PaperConsoleCommandSender.class).impl().sendMessage(in, message);
    }

    /**
     * Sends a chat message.
     *
     * @param message a message
     * @see Component
     * @since 4.1.0
     */
    public static void sendMessage(ConsoleCommandSender in, ComponentLike message) {
        CachedVersionWrapper.of(PaperConsoleCommandSender.class).impl().sendMessage(in, message);
    }
}
