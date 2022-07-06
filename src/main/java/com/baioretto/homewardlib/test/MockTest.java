package com.baioretto.homewardlib.test;

import com.baioretto.homewardlib.api.block.placer.BlockPlacer;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_18_R2.block.CraftBlock;
import org.bukkit.craftbukkit.v1_18_R2.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

@Command("test")
@SuppressWarnings("unused")
public class MockTest extends CommandBase implements Listener {
    @SubCommand("place")
    public void testPlaceBlock(CommandSender commandSender) {
        if (!(commandSender instanceof Player player)) return;
        BlockPlacer.get().placeNoteBlock(player.getWorld(), 10, 1145, -60, 1145);
    }

    @SubCommand("get")
    public void testGetBlock(CommandSender commandSender) {
        if (!(commandSender instanceof CraftPlayer player)) return;

        CraftBlock block = (CraftBlock) player.getTargetBlockExact(5);

        if (block == null) return;
        CraftBlockState craftBlockState = (CraftBlockState) block.getState();

        BlockState blockState = craftBlockState.getHandle();

        blockState.getProperties().forEach(System.out::println);
    }

    @EventHandler
    public void chunk(ChunkLoadEvent event) {
        if (event.getChunk().getX() == 1145 >> 4 && event.getChunk().getZ() == 1145 >> 4) {
            System.out.printf("ChunkLoadEvent: %s\n", event.getChunk());
        }
    }

    @EventHandler
    public void chunk(ChunkUnloadEvent event) {
        if (event.getChunk().getX() == 1145 >> 4 && event.getChunk().getZ() == 1145 >> 4) {
            System.out.printf("ChunkUnloadEvent: %s\n", event.getChunk());
        }
    }
}