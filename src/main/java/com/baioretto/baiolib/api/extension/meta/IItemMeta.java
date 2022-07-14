package com.baioretto.baiolib.api.extension.meta;

import com.baioretto.baiolib.annotation.Instantiable;
import com.baioretto.baiolib.exception.BaioLibInternalException;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.List;

/**
 * The {@code IItemMeta} class extend from {@link ItemMeta}
 *
 * @author baioretto
 * @since 1.1.0
 */
@Instantiable
public abstract class IItemMeta {
    protected static Class<?> craftMetaItemClass;
    protected static Field displayNameField;
    protected static Field loreField;

    protected IItemMeta(String className, String displayNameFieldName, String loreFieldName) {
        if (craftMetaItemClass == null) {
            try {
                craftMetaItemClass = Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new BaioLibInternalException(e);
            }
        }

        if (displayNameField == null) {
            try {
                displayNameField = craftMetaItemClass.getDeclaredField(displayNameFieldName);
            } catch (NoSuchFieldException e) {
                throw new BaioLibInternalException(e);
            }
            displayNameField.setAccessible(true);
        }

        if (loreField == null) {
            try {
                loreField = craftMetaItemClass.getDeclaredField(loreFieldName);
            } catch (NoSuchFieldException e) {
                throw new BaioLibInternalException(e);
            }
            loreField.setAccessible(true);
        }
    }

    /**
     * Set meta display name.
     *
     * @param in          the {@link ItemMeta} instance
     * @param displayName the display name {@link Component}
     */
    protected abstract void displayName(ItemMeta in, Component displayName);

    /**
     * Set meta lore list.
     *
     * @param in   the {@link ItemMeta} instance
     * @param lore the lore list {@link Component}
     */
    protected abstract void lore(ItemMeta in, List<Component> lore);
}
