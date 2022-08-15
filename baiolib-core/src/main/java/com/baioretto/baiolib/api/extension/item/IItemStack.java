package com.baioretto.baiolib.api.extension.item;

import com.baioretto.baiolib.annotation.Instantiable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

/**
 * The {@code IItemStack} class extend from {@link ItemStack}
 *
 * @author baioretto
 * @since 1.1.0
 */
@Instantiable
public interface IItemStack {
    /**
     * Edits the {@link ItemMeta} of this stack.
     *
     * @param in       the {@link ItemStack} of this meta
     * @param consumer the meta consumer
     * @return {@code true} if the edit was successful, {@code false} otherwise
     */
    default boolean editMeta(ItemStack in, Consumer<? super ItemMeta> consumer) {
        return editMeta(in, ItemMeta.class, consumer);
    }

    /**
     * Edits the {@link ItemMeta} of this stack if the meta is of the specified type.
     *
     * @param in        the {@link ItemStack} of this meta
     * @param metaClass the type of meta to edit
     * @param consumer  the meta consumer
     * @param <M>       the meta type
     * @return {@code true} if the edit was successful, {@code false} otherwise
     */
    <M extends ItemMeta> boolean editMeta(ItemStack in, Class<M> metaClass, Consumer<? super M> consumer);
}
