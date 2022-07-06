package com.baioretto.baiolib.api.block.placer;

import com.baioretto.baiolib.annotation.Instantiable;
import org.bukkit.Location;
import org.bukkit.World;

@Instantiable
public interface IBlockPlacer {
    void placeNoteBlock(World world, int note, int x, int y, int z);

    default void placeNoteBlock(World world, int note, Location location) {
        placeNoteBlock(world, note, location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
}
