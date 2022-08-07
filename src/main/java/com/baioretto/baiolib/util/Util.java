package com.baioretto.baiolib.util;

import com.baioretto.baiolib.BaioLib;
import com.baioretto.baiolib.BaioLibConfig;
import com.baioretto.baiolib.api.Pool;
import com.baioretto.baiolib.api.extension.sender.command.PaperConsoleCommandSender;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
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
        return new Reflections(new ConfigurationBuilder()
                .addClassLoaders(classLoader)
                .addUrls(ClasspathHelper.forPackage(pkg, classLoader))
                .filterInputsBy(new FilterBuilder().includePackage(pkg)));
    }

    /**
     * Send a message to command line.
     *
     * @param message the message
     */
    public void sendConsoleMessage(Component message) {
        Pool.get(PaperConsoleCommandSender.class).impl().sendMessage(BaioLib.instance().getServer().getConsoleSender(), BaioLibConfig.prefix.append(message));
    }
}
