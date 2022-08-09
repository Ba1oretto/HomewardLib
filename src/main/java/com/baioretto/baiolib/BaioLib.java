package com.baioretto.baiolib;

import com.baioretto.baiolib.dev.DevelopMode;
import com.baioretto.baiolib.util.ServerUtil;
import lombok.Getter;
import lombok.experimental.Accessors;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Accessors(fluent = true)
public final class BaioLib extends JavaPlugin {
    @Getter
    private static BaioLib instance;

    @Getter
    private final Thread mainThread;

    @Getter
    private CommandManager commandManager;

    @Override
    public void onLoad() {
        ServerUtil.sendConsoleMessage(text("using dev profile: ", YELLOW).append(text(BaioLibConfig.IS_DEV_ENV, BaioLibConfig.IS_DEV_ENV ? GREEN : RED)));
    }

    @Override
    public void onEnable() {
        commandManager = new CommandManager(this, true);
        DevelopMode.doEnable();
    }

    @Override
    public void onDisable() {
        DevelopMode.doDisable();
    }

    public BaioLib() {
        instance = this;
        mainThread = Thread.currentThread();
    }
}
