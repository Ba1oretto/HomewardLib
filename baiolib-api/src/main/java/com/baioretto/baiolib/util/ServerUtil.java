package com.baioretto.baiolib.util;

import com.baioretto.baiolib.exception.NotActuallyBaioLibException;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

import java.util.function.Supplier;

/**
 * Server utilities
 *
 * @since 1.5.0
 * @author baioretto
 */
public class ServerUtil {
    /**
     * @param function supplier
     * @param <T>      type
     * @return T
     * @see Supplier
     */
    public <T> T get(Supplier<T> function) {
        return function.get();
    }

    /**
     * Send a message to command line.
     *
     * @param message the message
     */
    public void sendConsoleMessage(Component message) {
        throw new NotActuallyBaioLibException();
    }

    /**
     * reload server.
     *
     * @param sender the command sender
     */
    public void reload(CommandSender sender) {
        throw new NotActuallyBaioLibException();
    }
}
