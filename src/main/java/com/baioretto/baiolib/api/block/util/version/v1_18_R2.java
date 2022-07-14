package com.baioretto.baiolib.api.block.util.version;

import com.baioretto.baiolib.api.block.util.IBlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ThreadedLevelLightEngine;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.chunk.LevelChunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_18_R2.CraftChunk;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.util.CraftMagicNumbers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class v1_18_R2 implements IBlockUtil {
    @SuppressWarnings("unchecked")
    @Override
    public BlockState getNoteBlockState(NoteBlockInstrument instrument, int note, boolean powered) {
        net.minecraft.world.level.block.Block block = getBlock(Material.NOTE_BLOCK);
        BlockState blockState = block.defaultBlockState();
        StateDefinition<Block, BlockState> states = block.getStateDefinition();

        blockState = blockState
                .setValue((Property<NoteBlockInstrument>) states.getProperty("instrument"), instrument)
                .setValue((Property<Integer>) states.getProperty("note"), note)
                .setValue((Property<Boolean>) states.getProperty("powered"), powered);

        return blockState;
    }

    @Override
    public List<CraftChunk> getChunkNearby(CraftChunk center) {
        World world = center.getWorld();
        int x = center.getX(), z = center.getZ();

        CraftChunk right = getChunk(world, x + 1, z);
        CraftChunk rightTop = getChunk(world, x + 1, z + 1);
        CraftChunk rightBot = getChunk(world, x + 1, z - 1);
        CraftChunk top = getChunk(world, x, z + 1);
        CraftChunk bot = getChunk(world, x, z - 1);
        CraftChunk left = getChunk(world, x - 1, z);
        CraftChunk leftTop = getChunk(world, x - 1, z + 1);
        CraftChunk leftBot = getChunk(world, x - 1, z - 1);

        return Arrays.asList(right, rightTop, rightBot, top, center, bot, left, leftTop, leftBot);
    }

    @Override
    public BlockPos getBlockPosition(int x, int y, int z) {
        return new BlockPos(x, y, z);
    }

    @Override
    public Block getBlock(Material material) {
        return CraftMagicNumbers.getBlock(material);
    }

    @Override
    public CraftChunk getChunk(World world, int x, int z) {
        return (CraftChunk) world.getChunkAt(x >> 4, z >> 4);
    }

    @Override
    public LevelChunk getChunkHandler(org.bukkit.Chunk chunk) {
        return ((CraftChunk) chunk).getHandle();
    }

    @Override
    public ThreadedLevelLightEngine getLightEngine(World world) {
        return ((CraftWorld) world).getHandle().getChunkSource().getLightEngine();
    }

    @Override
    public boolean contains(Block block, LevelChunk chunk, int x, int y, int z) {
        return Objects.equals(chunk.getBlockState(getBlockPosition(x, y, z)).getBlock(), block);
    }
}