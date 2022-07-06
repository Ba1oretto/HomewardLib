package com.baioretto.homewardlib.api.block.placer;

import com.baioretto.homewardlib.annotation.Instantiable;
import org.bukkit.Location;
import org.bukkit.World;

@Instantiable
public interface IBlockPlacer {
    void placeNoteBlock(World world, int note, int x, int y, int z);

    default void placeNoteBlock(World world, int note, Location location) {
        placeNoteBlock(world, note, location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
}
