package com.baioretto.baiolib.command;

import com.baioretto.baiolib.api.Pool;
import com.baioretto.baiolib.api.block.placer.BlockPlacer;
import com.baioretto.baiolib.api.component.ComponentUtil;
import com.baioretto.baiolib.api.extension.meta.ItemMetaImpl;
import com.baioretto.baiolib.api.extension.stack.ItemStackImpl;
import com.baioretto.baiolib.api.player.PlayerImpl;
import lombok.experimental.ExtensionMethod;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_18_R2.block.CraftBlock;
import org.bukkit.craftbukkit.v1_18_R2.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

@Command("test")
@SuppressWarnings("unused")
@ExtensionMethod({ItemStackImpl.class, ItemMetaImpl.class, PlayerImpl.class})
public class MockTest extends CommandBase implements Listener {
    @SubCommand("place")
    public void testPlaceBlock(CommandSender commandSender) {
        if (!(commandSender instanceof Player player)) return;
        Pool.get(BlockPlacer.class).impl().placeNoteBlock(player.getWorld(), 10, 1145, -60, 1145);
    }

    @SubCommand("getItemStack")
    public void testItemStack(CommandSender commandSender) {
        if (!(commandSender instanceof Player player)) return;

        ItemStack itemStack = new ItemStack(Material.PAPER);
        itemStack.editMeta(meta -> {
            ArrayList<Component> lore = new ArrayList<>();
            lore.add(Component.text("line 1", NamedTextColor.AQUA).append(Component.text("line 1.1")));
            lore.add(Component.text("line 2", NamedTextColor.RED));

            meta.lore(lore);
            meta.displayName(Component.text("test"));
        });

        player.getInventory().addItem(itemStack);
    }

    @SubCommand("decomponent")
    public void testDecomponent(CommandSender commandSender) {
        if (!(commandSender instanceof Player player)) return;

        TextComponent message = (TextComponent) Component.text("https://github.com/Ba1oretto", NamedTextColor.AQUA)
                .clickEvent(ClickEvent.openUrl("https://github.com/Ba1oretto"))
                .decorate(TextDecoration.UNDERLINED);
        player.sendMessage(message);
    }

    @Default
    public void temporaryTest(CommandSender commandSender) {
        if (!(commandSender instanceof Player player)) return;

        ComponentUtil componentUtil1 = Pool.get(ComponentUtil.class);
        ComponentUtil componentUtil2 = Pool.get(ComponentUtil.class);
        System.out.println(componentUtil1 == componentUtil2);
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