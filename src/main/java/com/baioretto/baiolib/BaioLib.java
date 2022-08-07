package com.baioretto.baiolib;

import com.baioretto.baiolib.command.MockTest;
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

    @Override
    public void onLoad() {
        Util.sendConsoleMessage(text("using dev profile: ", YELLOW).append(text(BaioLibConfig.isDevEnv, BaioLibConfig.isDevEnv ? GREEN : RED)));
    }

    @Override
    public void onEnable() {
        new CommandManager(this).register(new MockTest());

        if (BaioLibConfig.isDevEnv) {
            if (Bukkit.getServer().getName().equalsIgnoreCase(BaioLibConfig.paper)) KeepAliveUpdater.start();
        }
    }

    @Override
    public void onDisable() {
        if (BaioLibConfig.isDevEnv) {
            KeepAliveUpdater.stop();
        }
    }

    public BaioLib() {
        instance = this;
        mainThread = Thread.currentThread();
    }
}
