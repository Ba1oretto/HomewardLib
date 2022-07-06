package com.baioretto.homewardlib;

import com.baioretto.homewardlib.test.MockTest;
import lombok.Getter;
import lombok.experimental.Accessors;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
@Accessors(fluent = true)
public final class HomewardLib extends JavaPlugin {
    @Getter
    private static HomewardLib instance;

    @Override
    public void onEnable() {
        // new CommandManager(this).register(new MockTest());
        //
        // Bukkit.getPluginManager().registerEvents(new MockTest(), this);
    }

    public HomewardLib() {
        instance = this;
    }
}
