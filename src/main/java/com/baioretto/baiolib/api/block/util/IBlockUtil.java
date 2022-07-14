package com.baioretto.baiolib.api.block.util;

import com.baioretto.baiolib.annotation.Instantiable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ThreadedLevelLightEngine;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.chunk.LevelChunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_18_R2.CraftChunk;

import java.util.List;

@Instantiable
@SuppressWarnings("unused")
public interface IBlockUtil {
    BlockState getNoteBlockState(NoteBlockInstrument instrument, int note, boolean powered);

    default BlockState getNoteBlockState(int note) {
        return getNoteBlockState(NoteBlockInstrument.IRON_XYLOPHONE, note, false);
    }

    /**
     * should only be used when center chunk loaded
     */
    List<CraftChunk> getChunkNearby(CraftChunk center);

    BlockPos getBlockPosition(int x, int y, int z);

    default BlockPos getBlockPosition(org.bukkit.block.Block block) {
        return getBlockPosition(block.getX(), block.getY(), block.getZ());
    }

    Block getBlock(Material material);

    CraftChunk getChunk(World world, int x, int z);

    LevelChunk getChunkHandler(org.bukkit.Chunk chunk);

    default LevelChunk getChunkHandler(org.bukkit.block.Block block) {
        return getChunkHandler(block.getChunk());
    }

    ThreadedLevelLightEngine getLightEngine(World world);

    boolean contains(Block block, LevelChunk chunk, int x, int y, int z);
}
