package com.baioretto.baiolib.api.extension.sender.command.version;

import com.baioretto.baiolib.api.extension.sender.command.IConsoleCommandSender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.ConsoleCommandSender;

public class v1_18_R2 implements IConsoleCommandSender {
    @Override
    public void sendMessage(ConsoleCommandSender in, Component messages) {
        in.sendMessage(LegacyComponentSerializer.legacySection().serialize(messages));
    }

    @Override
    public void sendMessage(ConsoleCommandSender in, ComponentLike messages) {
        this.sendMessage(in, messages.asComponent());
    }
}
