package com.baioretto.baiolib.dev;

import com.baioretto.baiolib.BaioLib;
import com.baioretto.baiolib.BaioLibConfig;
import com.baioretto.baiolib.command.MockTest;
import com.baioretto.baiolib.dev.command.ReloadCommand;
import com.baioretto.baiolib.dev.listener.CommandListener;
import com.baioretto.baiolib.dev.listener.PlayerListener;
import me.mattstudios.mf.base.CommandBase;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

/**
 * @author baioretto
 * @since 1.5.0
 */
public class DevelopMode {
    public static void doEnable() {
        if (!BaioLibConfig.IS_DEV_ENV) return;
        instance.enable();
    }

    public static void doDisable() {
        if (!BaioLibConfig.IS_DEV_ENV) return;
        instance.disable();
    }

    private void enable() {
        registerCommand(new MockTest(), new ReloadCommand());
        registerListener(new CommandListener(), new PlayerListener());

        KeepAliveUpdater.doStart();
    }

    private void disable() {
        KeepAliveUpdater.doStop();
    }

    private void registerListener(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
        }
    }

    private void registerCommand(CommandBase... commands) {
        commandManager.register(commands);
    }

    private final BaioLib plugin;
    private final CommandManager commandManager;

    private DevelopMode() {
        plugin = BaioLib.instance();
        commandManager = BaioLib.instance().commandManager();
    }

    private static final DevelopMode instance;
    static {
        instance = new DevelopMode();
    }
}
