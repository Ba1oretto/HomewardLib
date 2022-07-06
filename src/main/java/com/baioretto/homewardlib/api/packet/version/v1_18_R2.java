package com.baioretto.homewardlib.api.packet.version;

import com.baioretto.homewardlib.api.packet.IPacketUtil;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.lighting.LevelLightEngine;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.BitSet;
import java.util.function.Predicate;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class v1_18_R2 implements IPacketUtil {
    @Override
    public void send(Player player, Packet<?> packet) {
        ((CraftPlayer) player).getHandle().connection.send(packet);
    }

    @Override
    public ClientboundLevelChunkWithLightPacket chunkWithLightPacket(LevelChunk chunk, LevelLightEngine lightEngine, BitSet skyLight, BitSet blockLight, boolean trustEdges) {
        return new ClientboundLevelChunkWithLightPacket(chunk, lightEngine, skyLight, blockLight, trustEdges);
    }

    @Override
    public void sendAll(Stream<? extends Player> playerStream, Predicate<Player> predicate, Packet<?> packet) {
        playerStream.filter(predicate).forEach(player -> ((CraftPlayer) player).getHandle().connection.send(packet));
    }
}
