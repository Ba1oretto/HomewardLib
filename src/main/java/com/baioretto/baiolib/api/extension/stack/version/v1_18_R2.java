package com.baioretto.baiolib.api.extension.stack.version;

import com.baioretto.baiolib.api.extension.stack.IItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public class v1_18_R2 implements IItemStack {
    @SuppressWarnings("unchecked")
    @Override
    public <M extends ItemMeta> boolean editMeta(ItemStack in, Class<M> metaClass, Consumer<? super M> consumer) {
        final @Nullable ItemMeta meta = in.getItemMeta();
        if (metaClass.isInstance(meta)) {
            consumer.accept((M) meta);
            in.setItemMeta(meta);
            return true;
        }
        return false;
    }
}
