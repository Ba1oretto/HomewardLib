package com.baioretto.baiolib.api.extension.stack;

import com.baioretto.baiolib.api.Pool;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

/**
 * The {@code ItemStackImpl} class used with {@link lombok.experimental.ExtensionMethod}
 * @author baioretto
 * @since 1.1.0
 */
@SuppressWarnings("unused")
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
        return Pool.get(PaperItemStack.class).impl().editMeta(in, consumer);
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
        return Pool.get(PaperItemStack.class).impl().editMeta(in, metaClass, consumer);
    }
}
