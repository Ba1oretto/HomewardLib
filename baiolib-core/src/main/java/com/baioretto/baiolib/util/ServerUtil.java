package com.baioretto.baiolib.util;

import com.baioretto.baiolib.BaioLib;
import com.baioretto.baiolib.ServerConfig;
import com.baioretto.baiolib.api.CachedVersionWrapper;
import com.baioretto.baiolib.api.extension.console.PaperConsoleCommandSender;
import com.baioretto.baiolib.handler.MethodExecuteBehaviorHandler;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.function.Supplier;

/**
 * Server utilities
 *
 * @since 1.5.0
 * @author baioretto
 */
@UtilityClass
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
        CachedVersionWrapper.of(PaperConsoleCommandSender.class).impl().sendMessage(BaioLib.instance().getServer().getConsoleSender(), ServerConfig.PREFIX.append(message));
    }

    /**
     * handle method execute behavior
     *
     * @param async is run asynchronously
     * @param handler the handler
     */
    public static void handle(boolean async, MethodExecuteBehaviorHandler handler) {
        if (async)
            switch (ServerUtil.getProvider()) {
                case Paper -> handler.paperImpl();
                case CraftBukkit -> handler.craftBukkitImpl();
                default -> handler.defaultImpl();
            }
        else handler.nonAsyncImpl();
    }

    /**
     * reload server.
     *
     * @param sender the command sender
     */
    public void reload(CommandSender sender) {
        sendConsoleMessage(Component.text("Please note that this command is not supported and may cause issues.", NamedTextColor.RED));
        sendConsoleMessage(Component.text("If you encounter any issues please use the /doStop command to restart your server.", NamedTextColor.RED));
        sendConsoleMessage(Component.text("Reload doStart.", NamedTextColor.RED));

        Bukkit.reload();

        TextComponent successMessage = Component.text("Reload complete.", NamedTextColor.GREEN);
        CachedVersionWrapper.of(PaperConsoleCommandSender.class).impl().sendMessage(BaioLib.instance().getServer().getConsoleSender(), Component.text(sender.getName() + ": ", NamedTextColor.WHITE).append(successMessage));
    }

    /**
     * get server provider
     *
     * @return provider
     */
    public Provider getProvider() {
        switch (Bukkit.getServer().getName()) {
            case ServerConfig.PAPER -> {
                return Provider.Paper;
            }
            case ServerConfig.CRAFT_BUKKIT -> {
                return Provider.CraftBukkit;
            }
            default -> {
                return Provider.Unknown;
            }
        }
    }

    public enum Provider {
        Paper,
        CraftBukkit,
        Unknown
    }
}
