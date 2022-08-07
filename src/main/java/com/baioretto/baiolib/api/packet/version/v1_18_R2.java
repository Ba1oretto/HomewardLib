package com.baioretto.baiolib.api.packet.version;

import com.baioretto.baiolib.api.packet.IPacketUtil;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundChatPacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.lighting.LevelLightEngine;

import java.util.BitSet;
import java.util.UUID;


public class v1_18_R2 implements IPacketUtil {
    @Override
    public ClientboundLevelChunkWithLightPacket chunkWithLightPacket(LevelChunk chunk, LevelLightEngine lightEngine, BitSet skyLight, BitSet blockLight, boolean trustEdges) {
        return new ClientboundLevelChunkWithLightPacket(chunk, lightEngine, skyLight, blockLight, trustEdges);
    }

    @Override
    public ClientboundChatPacket chatPacket(Component component, ChatType chatType, UUID uuid) {
        return new ClientboundChatPacket(component, chatType, uuid);
    }
}
