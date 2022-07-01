package com.baioretto.homewardlib.api.blockutil;

import com.baioretto.homewardlib.annotation.Instantiable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.LevelChunk;
import org.bukkit.Material;

@Instantiable
@SuppressWarnings("unused")
public interface IBlockUtil {
    Block getBlock(Material material);
    boolean contains(Block block, LevelChunk chunk, int x, int y, int z);
    LevelChunk getHandle(org.bukkit.block.Block block);
    LevelChunk getHandle(org.bukkit.Chunk chunk);

    BlockPos getBlockPosition(int x, int y, int z);

    BlockPos getBlockPosition(org.bukkit.block.Block block);

    void setBlock(org.bukkit.block.Block block);

    void setBlock(org.bukkit.block.Block block, Material material);

    void setBlockWithDefaultData(org.bukkit.block.Block block);
}
