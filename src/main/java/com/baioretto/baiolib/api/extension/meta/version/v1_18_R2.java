package com.baioretto.baiolib.api.extension.meta.version;

import com.baioretto.baiolib.api.extension.meta.IItemMeta;
import com.baioretto.baiolib.util.ComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class v1_18_R2 extends IItemMeta {
    public v1_18_R2() {
        super("org.bukkit.craftbukkit.v1_18_R2.inventory.CraftMetaItem", "displayName", "lore");
    }

    @Override
    public void displayName(ItemMeta in, Component displayName) {
        try {
            displayNameField.set(in, GsonComponentSerializer.gson().serialize(displayName));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void lore(ItemMeta in, List<Component> lore) {
        List<String> loreList = new ArrayList<>();
        lore.forEach(component -> loreList.add(ComponentSerializer.serialize(component)));

        try {
            loreField.set(in, loreList);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}