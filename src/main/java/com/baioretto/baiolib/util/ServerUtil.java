package com.baioretto.baiolib.util;

import com.baioretto.baiolib.BaioLib;
import com.baioretto.baiolib.BaioLibConfig;
import com.baioretto.baiolib.api.Pool;
import com.baioretto.baiolib.api.extension.sender.command.PaperConsoleCommandSender;
import com.baioretto.baiolib.handler.MethodExecuteBehaviorHandler;
import io.netty.channel.Channel;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.function.Supplier;

/**
 * Server information
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
     * Get a {@link Reflections} from package with custom classloader.
     *
     * @param pkg         package
     * @param classLoader classloader
     * @return reflections instance
     */
    public Reflections getReflections(String pkg, ClassLoader... classLoader) {
        return new Reflections(new ConfigurationBuilder().addClassLoaders(classLoader).addUrls(ClasspathHelper.forPackage(pkg, classLoader)).filterInputsBy(new FilterBuilder().includePackage(pkg)));
    }

    /**
     * Send a message to command line.
     *
     * @param message the message
     */
    public void sendConsoleMessage(Component message) {
        Pool.get(PaperConsoleCommandSender.class).impl().sendMessage(BaioLib.instance().getServer().getConsoleSender(), BaioLibConfig.PREFIX.append(message));
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
        Pool.get(PaperConsoleCommandSender.class).impl().sendMessage(BaioLib.instance().getServer().getConsoleSender(), Component.text(sender.getName() + ": ", NamedTextColor.WHITE).append(successMessage));
    }

    /**
     * get player channel
     *
     * @param player player
     * @return player channel
     */
    public Channel getChannel(Player player) {
        return ((CraftPlayer) player).getHandle().connection.getConnection().channel;
    }

    /**
     * get server provider
     *
     * @return provider
     */
    public Provider getProvider() {
        switch (Bukkit.getServer().getName()) {
            case BaioLibConfig.PAPER -> {
                return Provider.Paper;
            }
            case BaioLibConfig.CRAFT_BUKKIT -> {
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
