package com.baioretto.homewardlib.api.blockutil.impl;

import com.baioretto.homewardlib.api.blockutil.IBlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.LevelChunk;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R2.CraftChunk;
import org.bukkit.craftbukkit.v1_18_R2.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_18_R2.util.CraftMagicNumbers;

import java.util.Objects;

import static org.bukkit.Material.AIR;
import static org.bukkit.Material.CHORUS_PLANT;

@SuppressWarnings({"JavadocDeclaration", "unused"})
public class v1_18_R2 implements IBlockUtil {
    private static final BlockState defaultBlockState;

    /**
     * @param material
     * @return Block
     */
    public Block getBlock(Material material) {
        return CraftMagicNumbers.getBlock(material);
    }

    /**
     * @param block
     * @param chunk
     * @param x
     * @param y
     * @param z
     * @return boolean
     */
    public boolean contains(Block block, LevelChunk chunk, int x, int y, int z) {
        return Objects.equals(chunk.getBlockState(getBlockPosition(x, y, z)).getBlock(), block);
    }

    /**
     * @param block
     * @return LevelChunk
     */
    public LevelChunk getHandle(org.bukkit.block.Block block) {
        return getHandle(block.getChunk());
    }

    /**
     * @param chunk
     * @return LevelChunk
     */
    public LevelChunk getHandle(org.bukkit.Chunk chunk) {
        return ((CraftChunk) chunk).getHandle();
    }

    /**
     * @param x
     * @param y
     * @param z
     * @return BlockPos
     */
    public BlockPos getBlockPosition(int x, int y, int z) {
        return new BlockPos(x, y, z);
    }

    /**
     * @param block
     * @return BlockPos
     */
    public BlockPos getBlockPosition(org.bukkit.block.Block block) {
        return new BlockPos(block.getX(), block.getY(), block.getZ());
    }

    /**
     * @param block
     */
    public void setBlock(org.bukkit.block.Block block) {
        BlockPos position = getBlockPosition(block);
        LevelChunk chunk = getHandle(block);
        chunk.setBlockState(
                position,
                Objects.equals(block.getType(), CHORUS_PLANT) ?
                        CraftBlockData.newData(block.getType(), "[down=true,east=true,north=true,south=true,up=true,west=true]").getState() :
                        getBlock(block.getType()).defaultBlockState(),
                false,
                false
        );
    }

    /**
     * @param block
     * @param material
     */
    public void setBlock(org.bukkit.block.Block block, Material material) {
        BlockPos position = getBlockPosition(block);
        LevelChunk chunk = getHandle(block);
        chunk.setBlockState(position, getBlock(material).defaultBlockState(), false, false);
    }

    /**
     * @param block
     */
    public void setBlockWithDefaultData(org.bukkit.block.Block block) {
        BlockPos position = getBlockPosition(block);
        LevelChunk chunk = getHandle(block);
        chunk.setBlockState(position, defaultBlockState, false, false);
    }

    static {
        defaultBlockState = CraftMagicNumbers.getBlock(AIR).defaultBlockState();
    }
}