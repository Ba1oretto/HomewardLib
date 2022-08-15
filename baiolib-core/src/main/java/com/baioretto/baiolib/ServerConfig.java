package com.baioretto.baiolib;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.AQUA;

/**
 * Plugin configuration class
 *
 * @since 1.3.0
 * @author baioretto
 */
@UtilityClass
public class ServerConfig {
    public final boolean IS_DEV_ENV = Boolean.getBoolean("baiolib.dev");
    public final Component PREFIX = text("[BaioLib] ", AQUA);
    public final String PAPER = "Paper";
    public final String CRAFT_BUKKIT = "CraftBukkit";
    public final String RELOAD = "reload";
    public final String CHANNEL_HANDLER_NAME = "baiolib_packet_handler";

    public final String CRAFT_BUKKIT_VERSION;
    public final String NET_MINECRAFT_VERSION;

    public final String MOCK_TEST_COMMAND_NAME = "mocktest";

    static {
        String[] packageNameArray = Bukkit.getServer().getClass().getPackageName().split("\\.");
        CRAFT_BUKKIT_VERSION = packageNameArray[packageNameArray.length - 1];

        String uselessVersion = 'v' + Bukkit.getVersion().split(" ")[2];
        uselessVersion = uselessVersion.substring(0, uselessVersion.length() - 1).replaceAll("[.]", "_");
        int position = uselessVersion.lastIndexOf("_") + 1;
        if (uselessVersion.split("_").length == 3)
            NET_MINECRAFT_VERSION = uselessVersion.substring(0, position) + 'R' + uselessVersion.substring(position);
        else NET_MINECRAFT_VERSION = uselessVersion;
    }
}
