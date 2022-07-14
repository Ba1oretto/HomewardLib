package com.baioretto.baiolib.api.extension.bukkit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The {@link org.bukkit.Bukkit} implementation class.
 *
 * @author baioretto
 * @since 1.1.0
 */
@SuppressWarnings("unused")
public class BukkitImpl {

    /**
     * Creates an empty inventory of type {@link InventoryType#CHEST} with the
     * specified size and title.
     *
     * @param owner the holder of the inventory, or null to indicate no holder
     * @param size a multiple of 9 as the size of inventory to create
     * @param title the title of the inventory, displayed when inventory is
     *     viewed
     * @return a new inventory
     * @throws IllegalArgumentException if the size is not a multiple of 9
     */
    public static Inventory createInventory(@Nullable InventoryHolder owner, int size, @NotNull Component title) {
        String titleString = LegacyComponentSerializer.legacySection().serialize(title);
        return Bukkit.createInventory(owner, size, titleString);
    }
}
