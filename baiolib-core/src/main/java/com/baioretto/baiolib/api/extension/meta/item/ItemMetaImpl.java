package com.baioretto.baiolib.api.extension.meta.item;

import com.baioretto.baiolib.api.CachedVersionWrapper;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * The {@code ItemMetaImpl} class used with {@link lombok.experimental.ExtensionMethod}
 *
 * @author baioretto
 * @since 1.1.0
 */
public class ItemMetaImpl {
    /**
     * Set meta display name.
     *
     * @param in          the {@link ItemMeta} instance
     * @param displayName the display name {@link Component}
     */
    public static void displayName(ItemMeta in, Component displayName) {
        CachedVersionWrapper.of(PaperItemMeta.class).impl().displayName(in, displayName);
    }

    /**
     * Set meta lore list.
     *
     * @param in   the {@link ItemMeta} instance
     * @param lore the lore list {@link Component}
     */
    public static void lore(ItemMeta in, List<Component> lore) {
        CachedVersionWrapper.of(PaperItemMeta.class).impl().lore(in, lore);
    }

    /**
     * Get meta lore list.
     *
     * @param in   the {@link ItemMeta} instance
     * @return null if no lore exist, otherwise return component list
     */
    public static @Nullable List<Component> lore(ItemMeta in) {
        return CachedVersionWrapper.of(PaperItemMeta.class).impl().lore(in);
    }

    /**
     * Set meta display name list.
     *
     * @param in   the {@link ItemMeta} instance
     * @return null if no display name can be cast to component, otherwise return component
     */
    public static @Nullable Component displayName(ItemMeta in) {
        return CachedVersionWrapper.of(PaperItemMeta.class).impl().displayName(in);
    }
}
