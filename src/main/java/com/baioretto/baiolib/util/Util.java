package com.baioretto.baiolib.util;

import com.baioretto.baiolib.BaioLib;
import com.baioretto.baiolib.BaioLibConfig;
import com.baioretto.baiolib.api.Pool;
import com.baioretto.baiolib.api.extension.sender.command.PaperConsoleCommandSender;
import com.baioretto.baiolib.api.extension.sender.player.PlayerImpl;
import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.function.Supplier;

/**
 * Almost bridge.
 *
 * @author baioretto
 * @since 1.1.0
 */
@UtilityClass
@ExtensionMethod(PlayerImpl.class)
public class Util {
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
     * reload server.
     *
     * @param sender the command sender
     */
    public void reload(CommandSender sender) {
        Util.sendConsoleMessage(Component.text("Please note that this command is not supported and may cause issues.", NamedTextColor.RED));
        Util.sendConsoleMessage(Component.text("If you encounter any issues please use the /doStop command to restart your server.", NamedTextColor.RED));
        Util.sendConsoleMessage(Component.text("Reload doStart.", NamedTextColor.RED));

        Bukkit.reload();

        TextComponent successMessage = Component.text("Reload complete.", NamedTextColor.GREEN);
        Pool.get(PaperConsoleCommandSender.class).impl().sendMessage(BaioLib.instance().getServer().getConsoleSender(), Component.text(sender.getName() + ": ", NamedTextColor.WHITE).append(successMessage));
    }
}
