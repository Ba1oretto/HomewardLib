package com.baioretto.baiolib.api.extension.meta.version;

import com.baioretto.baiolib.api.Pool;
import com.baioretto.baiolib.api.component.ComponentUtil;
import com.baioretto.baiolib.api.component.IComponentUtil;
import com.baioretto.baiolib.api.extension.meta.IItemMeta;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
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
            displayNameField.set(in, Pool.get(ComponentUtil.class).impl().decomponent((TextComponent) displayName).toString());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void lore(ItemMeta in, List<Component> lore) {
        IComponentUtil componentUtil = Pool.get(ComponentUtil.class).impl();
        List<String> loreList = new ArrayList<>();
        lore.forEach(l -> loreList.add(componentUtil.decomponent((TextComponent) l).toString()));

        try {
            loreField.set(in, loreList);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
