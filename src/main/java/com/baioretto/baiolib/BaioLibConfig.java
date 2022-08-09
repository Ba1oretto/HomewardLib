package com.baioretto.baiolib;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.AQUA;

/**
 * Contains JMV arguments
 *
 * @since 1.3.0
 * @author baioretto
 */
@UtilityClass
public class BaioLibConfig {
    public final boolean IS_DEV_ENV = Boolean.getBoolean("baiolib.dev");
    public final Component PREFIX = text("[BaioLib] ", AQUA);
    public final String PAPER = "Paper";
    public final String CRAFT_BUKKIT = "CraftBukkit";
    public final String RELOAD = "reload";
    public final String CHANNEL_HANDLER_NAME = "baiolib_packet_handler";
}
