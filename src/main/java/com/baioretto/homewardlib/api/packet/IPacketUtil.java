package com.baioretto.homewardlib.api.packet;

import com.baioretto.homewardlib.annotation.Instantiable;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.lighting.LevelLightEngine;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.BitSet;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Instantiable
public interface IPacketUtil {
    void send(Player player, Packet<?> packet);

    ClientboundLevelChunkWithLightPacket chunkWithLightPacket(LevelChunk chunk, LevelLightEngine lightEngine, BitSet skyLight, BitSet blockLight, boolean trustEdges);

    default ClientboundLevelChunkWithLightPacket chunkWithLightPacket(LevelChunk chunk, LevelLightEngine lightEngine) {
        return chunkWithLightPacket(chunk, lightEngine, null, null, false);
    }

    void sendAll(Stream<? extends Player> playerStream, Predicate<Player> predicate, Packet<?> packet);
}


