package com.baioretto.baiolib.command;

import com.baioretto.baiolib.BaioLib;
import com.baioretto.baiolib.api.Pool;
import com.baioretto.baiolib.api.extension.bukkit.BukkitImpl;
import com.baioretto.baiolib.api.extension.meta.ItemMetaImpl;
import com.baioretto.baiolib.api.extension.sender.command.ConsoleCommandSenderImpl;
import com.baioretto.baiolib.api.extension.sender.player.PaperPlayer;
import com.baioretto.baiolib.api.extension.stack.ItemStackImpl;
import com.baioretto.baiolib.api.extension.sender.player.PlayerImpl;
import de.tr7zw.nbtapi.NBTItem;
import lombok.SneakyThrows;
import lombok.experimental.ExtensionMethod;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
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
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

@Command("baiolibtest")

@ExtensionMethod({ItemStackImpl.class, ItemMetaImpl.class, PlayerImpl.class, ConsoleCommandSenderImpl.class, BukkitImpl.class})
public class MockTest extends CommandBase implements Listener {
    @Default
    public void defaultTest(CommandSender commandSender) {
    }

    @SubCommand("iConsoleCommandSender$sendMessage")
    public void iConsoleCommandSender$sendMessage(CommandSender commandSender) {
        if (!(commandSender instanceof Player player)) return;
        BaioLib.instance().getServer().getConsoleSender().sendMessage(Component.text("test", NamedTextColor.AQUA));
    }

    @SubCommand("testItemMeta")
    public void testItemMeta(CommandSender commandSender) {
        if (!(commandSender instanceof Player player)) return;

        ItemStack itemStack = new ItemStack(Material.PAPER);
        itemStack.editMeta(meta -> {
            // meta.displayName(Component.text("test"));
            // System.out.println(meta.displayName());

            ArrayList<Component> components = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                components.add(Component.text(String.format("line: %s", i)));
            }

            meta.lore(components);

            System.out.println(meta.lore());
        });

        player.getInventory().addItem(itemStack);
    }

    @SubCommand("testSerializeV1")
    public void testSerializeV1(CommandSender commandSender) {
        if (!(commandSender instanceof Player player)) return;
        TextComponent textComponent = Component.text("1", NamedTextColor.DARK_BLUE)
                .append(Component.text("2").color(TextColor.color(255, 192, 203)).decorate(TextDecoration.BOLD))
                .append(Component.text("3")
                        .append(Component.text("3.1").hoverEvent(HoverEvent.showEntity(HoverEvent.ShowEntity.of(Key.key("minecraft:zombie"), UUID.randomUUID())))));

        Pool.get(PaperPlayer.class).impl().sendMessage(player, textComponent);
    }

    @SubCommand("testSerializeV2")
    public void testSerializeV2(CommandSender commandSender) {
        if (!(commandSender instanceof Player player)) return;

        TextComponent textComponent = Component.text("1", NamedTextColor.GREEN)
                .append(Component.text("2").color(TextColor.color(255, 192, 203)).decorate(TextDecoration.BOLD))
                .append(Component.text("3")
                        .append(Component.text("3.1").clickEvent(ClickEvent.openUrl("https://wiki.vg/Chat"))
                                .append(Component.text("3.1.1"))
                                .append(Component.text("3.1.2"))));

        Pool.get(PaperPlayer.class).impl().sendMessage(player, textComponent);
    }

    @SubCommand("testDeserialize")
    @SneakyThrows
    public void testDeserialize(CommandSender commandSender) {
        if (!(commandSender instanceof Player player)) return;

        ItemStack itemStack = new ItemStack(Material.PAPER);
        itemStack.editMeta(meta -> {
            TextComponent textComponent = Component.text("1", NamedTextColor.DARK_BLUE)
                    .append(Component.text("2").color(TextColor.color(255, 192, 203)).decorate(TextDecoration.BOLD))
                    .append(Component.text("3")
                            .append(Component.text("3.1").hoverEvent(HoverEvent.showEntity(HoverEvent.ShowEntity.of(Key.key("minecraft:zombie"), UUID.randomUUID())))));

            meta.displayName(textComponent);
        });

        System.out.println(new NBTItem(itemStack));

        ItemMeta itemMeta = itemStack.getItemMeta();

        Field field = Class.forName("org.bukkit.craftbukkit.v1_18_R2.inventory.CraftMetaItem").getDeclaredField("displayName");
        field.setAccessible(true);

        Object object = field.get(itemMeta);
        String json = object.toString();

        System.out.printf("json: %s%n", json);

        Component component = GsonComponentSerializer.gson().deserialize(json);

        System.out.printf("textComponent: %s%n", component);

        Pool.get(PaperPlayer.class).impl().sendMessage(player, component);
    }

    @SubCommand("get")
    public void testGetBlock(CommandSender commandSender) {
        if (!(commandSender instanceof CraftPlayer player)) return;

        CraftBlock block = (CraftBlock) player.getTargetBlockExact(5);

        if (block == null) return;
        System.out.println(block.getHandle().getBlockState(block.getPosition()).getValues());
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