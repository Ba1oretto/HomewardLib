package com.baioretto.homewardlib;

import com.baioretto.homewardlib.command.MockTest;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class HomewardLib extends JavaPlugin{

    @Override
    public void onEnable() {
        new CommandManager(this).register(new MockTest());
    }
}
