package com.baioretto.baiolib;

import com.baioretto.baiolib.command.MockTest;
import com.baioretto.baiolib.dev.CommandListener;
import com.baioretto.baiolib.dev.ReloadCommand;
import com.baioretto.baiolib.dev.KeepAliveUpdater;
import com.baioretto.baiolib.util.Util;
import lombok.Getter;
import lombok.experimental.Accessors;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Accessors(fluent = true)
public final class BaioLib extends JavaPlugin {
    @Getter
    private static BaioLib instance;

    @Getter
    private final Thread mainThread;

    private CommandManager commandManager;

    @Override
    public void onLoad() {
        Util.sendConsoleMessage(text("using dev profile: ", YELLOW).append(text(BaioLibConfig.IS_DEV_ENV, BaioLibConfig.IS_DEV_ENV ? GREEN : RED)));
    }

    @Override
    public void onEnable() {
        commandManager = new CommandManager(this, true);
        enableDevelopModeFunction();
    }

    @Override
    public void onDisable() {
        disableDevelopModeFunction();
    }

    private void enableDevelopModeFunction() {
        if (!BaioLibConfig.IS_DEV_ENV) return;
        commandManager.register(new MockTest(), new ReloadCommand());

        Bukkit.getPluginManager().registerEvents(new CommandListener(), this);

        if (Bukkit.getServer().getName().equalsIgnoreCase(BaioLibConfig.PAPER)) KeepAliveUpdater.doStart();
    }

    private void disableDevelopModeFunction() {
        if (!BaioLibConfig.IS_DEV_ENV) return;
        KeepAliveUpdater.doStop();
    }

    public BaioLib() {
        instance = this;
        mainThread = Thread.currentThread();
    }
}
