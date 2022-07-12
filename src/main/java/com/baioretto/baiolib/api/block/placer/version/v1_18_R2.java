package com.baioretto.baiolib.api.block.placer.version;

import com.baioretto.baiolib.BaioLib;
import com.baioretto.baiolib.api.Pool;
import com.baioretto.baiolib.api.block.placer.IBlockPlacer;
import com.baioretto.baiolib.api.block.util.BlockUtil;
import com.baioretto.baiolib.api.block.util.IBlockUtil;
import com.baioretto.baiolib.api.packet.IPacketUtil;
import com.baioretto.baiolib.api.packet.PacketUtil;
import com.baioretto.baiolib.api.player.IPlayerUtil;
import com.baioretto.baiolib.api.player.PlayerUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.server.level.ThreadedLevelLightEngine;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_18_R2.CraftChunk;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.HashSet;

@SuppressWarnings("unused")
public class v1_18_R2 implements IBlockPlacer {
    @Override
    public void placeNoteBlock(World world, int note, int x, int y, int z) {
        IBlockUtil blockUtil = Pool.get(BlockUtil.class).impl();
        IPacketUtil packetUtil = Pool.get(PacketUtil.class).impl();

        BlockState noteBlockState = blockUtil.getNoteBlockState(note);
        BlockPos targetBlockPosition = blockUtil.getBlockPosition(x, y, z);

        boolean skipUpdate = Arrays.stream(world.getLoadedChunks()).noneMatch(chunk -> chunk.getX() == x >> 4 && chunk.getZ() == z >> 4);

        CraftChunk craftCenterChunk = blockUtil.getChunk(world, x, z);
        LevelChunk levelCenterChunk = craftCenterChunk.getHandle();
        ThreadedLevelLightEngine lightEngine = blockUtil.getLightEngine(world);

        levelCenterChunk.setBlockState(targetBlockPosition, noteBlockState, false);

        if (skipUpdate) return;

        lightEngine.checkBlock(targetBlockPosition);

        int dist = Bukkit.getViewDistance() + 1;
        ClientboundLevelChunkWithLightPacket chunkWithLightPacket = packetUtil.chunkWithLightPacket(levelCenterChunk, lightEngine);

        IPlayerUtil playerUtil = Pool.get(PlayerUtil.class).impl();

        // first update player in chunk
        HashSet<CraftPlayer> updatedPlayers = new HashSet<>();
        blockUtil.getChunkNearby(craftCenterChunk).forEach(chunk -> Arrays.stream(chunk.getEntities()).filter(entity -> entity.getType().equals(EntityType.PLAYER)).forEach(entity -> {
            updatedPlayers.add((CraftPlayer) entity);
            playerUtil.send((CraftPlayer) entity, chunkWithLightPacket);
        }));

        // then all player except player in chunk
        Bukkit.getScheduler().runTaskAsynchronously(BaioLib.instance(), () -> playerUtil.sendAll(Bukkit.getOnlinePlayers().stream(), onlinePlayer -> {
            ChunkPos chunkPosition = ((CraftPlayer) onlinePlayer).getHandle().chunkPosition();
            int chunkX = chunkPosition.x;
            int chunkZ = chunkPosition.z;
            return !(updatedPlayers.contains((CraftPlayer) onlinePlayer) || levelCenterChunk.getPos().x < chunkX - dist || levelCenterChunk.getPos().x > chunkX + dist || levelCenterChunk.getPos().z < chunkZ - dist || levelCenterChunk.getPos().z > chunkZ + dist);
        }, chunkWithLightPacket));
    }
}
