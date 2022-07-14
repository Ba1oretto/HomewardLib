package com.baioretto.baiolib.api.extension.sender.command;

import com.baioretto.baiolib.annotation.Instantiable;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * The {@code IConsoleCommandSender} class provides server bound method.
 *
 * @author baioretto
 * @since 1.1.0
 */
@Instantiable
public interface IConsoleCommandSender {
    /**
     * Send a {@link Component} message
     *
     * @param in       the console instance
     * @param messages the message
     * @see Component
     */
    void sendMessage(ConsoleCommandSender in, Component messages);

    /**
     * Send a {@link Component} message
     *
     * @param in       the console instance
     * @param messages the message
     * @see Component
     */
    default void sendMessage(ConsoleCommandSender in, ComponentLike messages) {
        sendMessage(in, messages.asComponent());
    }
}
