package com.baioretto.baiolib.api.extension.item;

import com.baioretto.baiolib.api.CachedVersionWrapper;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The {@code ItemStackImpl} class used with {@link lombok.experimental.ExtensionMethod}
 * @author baioretto
 * @since 1.1.0
 */
public class ItemStackImpl {
    /**
     * Edits the {@link ItemMeta} of this stack.
     *
     * @param in the {@link ItemStack} of this meta
     * @param consumer the meta consumer
     * @return {@code true} if the edit was successful, {@code false} otherwise
     */
    @SuppressWarnings("UnusedReturnValue")
    public static boolean editMeta(final ItemStack in, final @NotNull java.util.function.Consumer<? super ItemMeta> consumer) {
        return CachedVersionWrapper.of(PaperItemStack.class).impl().editMeta(in, consumer);
    }

    /**
     * Edits the {@link ItemMeta} of this stack if the meta is of the specified type.
     *
     * @param in the {@link ItemStack} of this meta
     * @param metaClass the type of meta to edit
     * @param consumer  the meta consumer
     * @param <M>       the meta type
     * @return {@code true} if the edit was successful, {@code false} otherwise
     */
    public static <M extends ItemMeta> boolean editMeta(final ItemStack in, Class<M> metaClass, final @NotNull java.util.function.Consumer<? super ItemMeta> consumer) {
        return CachedVersionWrapper.of(PaperItemStack.class).impl().editMeta(in, metaClass, consumer);
    }

    /**
     * Set persistent data to item.
     *
     * @param in {@code ItemStack} instance
     * @param type {@link PersistentDataType}
     * @return true if success
     * @param <T> generic type
     */
    public static <T> boolean set(final ItemStack in, NamespacedKey key, PersistentDataType<?, T> type, T value) {
        return editMeta(in, meta -> meta.getPersistentDataContainer().set(key, type, value));
    }

    /**
     * Get persistent data from item.
     *
     * @param in {@code ItemStack} instance
     * @param type {@link PersistentDataType}
     * @return true if success
     */
    public static @Nullable <T> T get(final ItemStack in, NamespacedKey key, PersistentDataType<?, T> type) {
        ItemMeta meta = in.getItemMeta();
        return meta == null ? null : meta.getPersistentDataContainer().get(key, type);
    }
}