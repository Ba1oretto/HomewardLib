package com.baioretto.baiolib.api.extension.meta.version;

import com.baioretto.baiolib.api.extension.meta.IItemMeta;
import com.baioretto.baiolib.exception.BaioLibInternalException;
import com.baioretto.baiolib.util.ComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


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
            throw new BaioLibInternalException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected @Nullable List<Component> lore(ItemMeta in) {
        Object loreListObject;
        try {
            loreListObject = loreField.get(in);
        } catch (IllegalAccessException e) {
            return null;
        }
        if (loreListObject == null) return null;

        List<String> loreList = (List<String>) loreListObject;

        ArrayList<Component> components = new ArrayList<>(loreList.size());
        loreList.forEach(s -> components.add(ComponentSerializer.deserialize(s)));
        return components;
    }

    @Override
    protected @Nullable Component displayName(ItemMeta in) {
        Object displayName;
        try {
            displayName = displayNameField.get(in);
        } catch (IllegalAccessException e) {
            return null;
        }
        if (displayName == null) return null;

        return ComponentSerializer.deserialize(displayName.toString());
    }
}