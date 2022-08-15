package com.baioretto.baiolib;

import com.baioretto.baiolib.dev.DevelopMode;
import com.baioretto.baiolib.util.ServerUtil;
import lombok.Getter;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.jetbrains.annotations.NotNull;

import java.io.File;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public class BaioLib extends JavaPlugin {
    @Getter
    private static BaioLib instance;

    @Override
    public void onLoad() {
        ServerUtil.sendConsoleMessage(text("using dev profile: ", YELLOW).append(text(ServerConfig.IS_DEV_ENV, ServerConfig.IS_DEV_ENV ? GREEN : RED)));
    }

    @Override
    public void onEnable() {
        DevelopMode.doEnable();
    }

    @Override
    public void onDisable() {
    }

    public BaioLib() {
        instance = this;
    }

    protected BaioLib(@NotNull JavaPluginLoader loader, @NotNull PluginDescriptionFile description, @NotNull File dataFolder, @NotNull File file) {
        super(loader, description, dataFolder, file);
    }
}
