package com.baioretto.baiolib;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
@Accessors(fluent = true)
public final class BaioLib extends JavaPlugin {
    @Getter
    private static BaioLib instance;

    @Override
    public void onEnable() {
        // new CommandManager(this).register(new MockTest());
        //
        // Bukkit.getPluginManager().registerEvents(new MockTest(), this);
    }

    public BaioLib() {
        instance = this;
    }
}
