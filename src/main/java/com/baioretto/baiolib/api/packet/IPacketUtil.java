package com.baioretto.baiolib.api.packet;

import com.baioretto.baiolib.annotation.Instantiable;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundChatPacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.lighting.LevelLightEngine;

import java.util.BitSet;
import java.util.UUID;

/**
 * The {@code IPacketUtil} interface
 *
 * @author baioretto
 * @since 1.0.0
 */
@Instantiable
@SuppressWarnings("unused")
public interface IPacketUtil {
    /**
     * Get {@link ClientboundLevelChunkWithLightPacket} from given parameters
     *
     * @param chunk       the chunk
     * @param lightEngine the light engine
     * @return {@link ClientboundLevelChunkWithLightPacket}
     */
    default ClientboundLevelChunkWithLightPacket chunkWithLightPacket(LevelChunk chunk, LevelLightEngine lightEngine) {
        return chunkWithLightPacket(chunk, lightEngine, null, null, false);
    }

    /**
     * Get {@link ClientboundLevelChunkWithLightPacket} from given parameters
     *
     * @param chunk       the chunk
     * @param lightEngine the light engine
     * @param skyLight    the skylight
     * @param blockLight  the block light
     * @param trustEdges  the trust edges
     * @return {@link ClientboundLevelChunkWithLightPacket}
     */
    ClientboundLevelChunkWithLightPacket chunkWithLightPacket(LevelChunk chunk, LevelLightEngine lightEngine, BitSet skyLight, BitSet blockLight, boolean trustEdges);

    /**
     * Get {@link ClientboundChatPacket} by {@link Component}
     *
     * @param component the component message
     * @return {@link ClientboundChatPacket}
     */
    default ClientboundChatPacket chatPacket(Component component) {
        return chatPacket(component, ChatType.SYSTEM, UUID.randomUUID());
    }

    /**
     * Get {@link ClientboundChatPacket} by {@link Component}
     *
     * @param component the {@link Component} message
     * @param chatType  the {@link ChatType}
     * @param uuid      the {@link UUID}
     * @return {@link ClientboundChatPacket}
     */
    ClientboundChatPacket chatPacket(Component component, ChatType chatType, UUID uuid);
}