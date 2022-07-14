package com.baioretto.baiolib.api.extension.sender.command;

import com.baioretto.baiolib.api.Pool;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.command.ConsoleCommandSender;

/**
 * The {@link ConsoleCommandSender} implementation.
 *
 * @author baioretto
 * @since 1.1.0
 */
@SuppressWarnings("unused")
public class ConsoleCommandSenderImpl {
    /**
     * Sends a chat message.
     *
     * @param message a message
     * @see Component
     * @since 4.1.0
     */
    public static void sendMessage(ConsoleCommandSender in, Component message) {
        Pool.get(PaperConsoleCommandSender.class).impl().sendMessage(in, message);
    }

    /**
     * Sends a chat message.
     *
     * @param message a message
     * @see Component
     * @since 4.1.0
     */
    public static void sendMessage(ConsoleCommandSender in, ComponentLike message) {
        Pool.get(PaperConsoleCommandSender.class).impl().sendMessage(in, message);
    }
}
