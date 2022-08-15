package com.baioretto.baiolib.dev;

import com.baioretto.baiolib.BaioLib;
import com.baioretto.baiolib.ServerConfig;
import com.baioretto.baiolib.dev.command.UnitTestCommand;
import com.baioretto.baiolib.dev.listener.CommandListener;
import com.baioretto.baiolib.util.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;

/**
 * @author baioretto
 * @since 1.5.0
 */
public class DevelopMode {
    public static void doEnable() {
        if (!ServerConfig.IS_DEV_ENV) return;
        instance.enable();
    }

    private void enable() {
        registerListener(new CommandListener());
        registerCommand(new UnitTestCommand());
    }

    private void registerCommand(Command... commands) {
        for (final Command command : commands) {
            commandMap.register(command.getName(), plugin.getName(), command);
        }
    }

    private void registerListener(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
        }
    }

    private final BaioLib plugin;
    private final CommandMap commandMap;

    private DevelopMode() {
        plugin = BaioLib.instance();
        Server server = Bukkit.getServer();
        commandMap = (CommandMap) ReflectionUtil.invoke(ReflectionUtil.getMethod(server.getClass(), "getCommandMap"), server);
    }

    private static final DevelopMode instance = new DevelopMode();
}
