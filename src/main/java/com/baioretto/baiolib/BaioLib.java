package com.baioretto.baiolib;

import com.baioretto.baiolib.command.MockTest;
import lombok.Getter;
import lombok.experimental.Accessors;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Accessors(fluent = true)
public final class BaioLib extends JavaPlugin {
    @Getter
    private static BaioLib instance;

    @Override
    public void onEnable() {
        new CommandManager(this).register(new MockTest());

        Bukkit.getPluginManager().registerEvents(new MockTest(), this);
    }

    public BaioLib() {
        instance = this;
    }
}
