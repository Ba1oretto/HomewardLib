package com.baioretto.baiolib;

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
public class ServerConfig {
    public final static boolean IS_DEV_ENV = Boolean.getBoolean("baiolib.dev");
    public final static Component PREFIX = text("[BaioLib] ", AQUA);
    public final static String PAPER = "Paper";
    public final static String CRAFT_BUKKIT = "CraftBukkit";
    public final static String RELOAD = "reload";
    public final static String CHANNEL_HANDLER_NAME = "baiolib_packet_handler";

    public final static String CRAFT_BUKKIT_VERSION;
    public final static String NET_MINECRAFT_VERSION;

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
