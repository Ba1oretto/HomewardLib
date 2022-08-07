package com.baioretto.baiolib;

import net.kyori.adventure.text.Component;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.AQUA;

/**
 * Contains JMV arguments
 *
 * @since 1.3.0
 * @author baioretto
 */
public class BaioLibConfig {
    public final static boolean isDevEnv = Boolean.getBoolean("baiolib.dev");
    public final static Component prefix = text("[BaioLib] ", AQUA);
    public final static String paper = "Paper";
}
