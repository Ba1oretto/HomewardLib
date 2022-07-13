package com.baioretto.baiolib.command;

import com.baioretto.baiolib.api.Pool;
import com.baioretto.baiolib.api.block.placer.BlockPlacer;
import com.baioretto.baiolib.api.player.PlayerUtil;
import com.baioretto.baiolib.config.TextComponentTypeAdapter;
import com.baioretto.baiolib.util.Util;
import com.baioretto.baiolib.util.Validate;
import com.google.common.base.CaseFormat;
import com.google.gson.*;
import lombok.SneakyThrows;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Command("test")
@SuppressWarnings("unused")
// @ExtensionMethod({ItemStackImpl.class, ItemMetaImpl.class, PlayerImpl.class})
public class MockTest extends CommandBase implements Listener {
    private final Gson gson = Util.get(() -> new GsonBuilder().registerTypeAdapter(TextComponent.class, new TextComponentTypeAdapter()).create());

    @SubCommand("testDecomponentV2")
    public void testDecomponentV2(CommandSender commandSender) {
        if (!(commandSender instanceof Player player)) return;

        TextComponent textComponent = Component.text("1", NamedTextColor.GREEN)
                    .append(Component.text("2").color(TextColor.color(255, 192, 203)).decorate(TextDecoration.BOLD))
                    .append(Component.text("3")
                            .append(Component.text("3.1").clickEvent(ClickEvent.openUrl("https://wiki.vg/Chat"))
                                    .append(Component.text("3.1.1"))
                                    .append(Component.text("3.1.2"))));

        Pool.get(PlayerUtil.class).impl().sendMessage(player, textComponent);
    }

    @SubCommand("testDecomponentV1")
    public void testDecomponentV1(CommandSender commandSender) {
        if (!(commandSender instanceof Player player)) return;

        HoverEvent<HoverEvent.ShowItem> hoverEvent = HoverEvent.showItem(HoverEvent.ShowItem.of(Material.PAPER.getKey(), 2));
        // HoverEvent<Component> hoverEvent = HoverEvent.showText(Component.text("www"));
        // HoverEvent<HoverEvent.ShowEntity> hoverEvent = HoverEvent.showEntity(NamespacedKey.minecraft("zombie"), UUID.randomUUID(), Component.text("test", TextColor.color(255, 192, 203)));
        ClickEvent clickEvent = ClickEvent.openUrl("https://github.com/Ba1oretto");
        TextComponent textComponent = Component.text("1").hoverEvent(hoverEvent);

        TypeAdapter<TextComponent> adapter = gson.getAdapter(TextComponent.class);
        Pool.get(PlayerUtil.class).impl().sendMessage(player, adapter.toJson(textComponent));
    }

    @SneakyThrows
    @Default
    private TextComponent test(JsonElement element) {
        JsonObject object = element.getAsJsonObject();

        TextComponent.Builder builder = Component.text();

        builder.content(object.get("text").getAsString());

        applyBoolean(object, "bold", builder);
        applyBoolean(object, "italic", builder);
        applyBoolean(object, "underlined", builder);
        applyBoolean(object, "strikethrough", builder);
        applyBoolean(object, "obfuscated", builder);

        // JsonElement fontElement = object.get("font");
        // if (Validate.notNull(fontElement)) {
        //     String asString = fontElement.getAsString();
        //     builder.font(Key.key(asString));
        // }

        JsonElement colorElement = object.get("color");
        if (Validate.notNull(colorElement)) {
            builder.color(TextColor.fromHexString(colorElement.getAsString()));
        }

        JsonElement insertionElement = object.get("insertion");
        if (Validate.notNull(insertionElement)) {
            builder.insertion(insertionElement.getAsString());
        }

        JsonElement clickEventElement = object.get("clickEvent");
        if (Validate.notNull(clickEventElement)) {
            JsonObject clickEventObject = clickEventElement.getAsJsonObject();
            String action = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, clickEventObject.get("action").getAsString().toLowerCase(Locale.ROOT));
            try {
                Method method = ClickEvent.class.getMethod(action, String.class);
                try {
                    ClickEvent clickEvent = (ClickEvent) method.invoke(null, clickEventObject.get("value").getAsString());
                    builder.clickEvent(clickEvent);
                } catch (IllegalAccessException | InvocationTargetException ignore) {
                }
            } catch (NoSuchMethodException ignore) {
            }
        }

        JsonElement hoverEventElement = object.get("hoverEvent");

        JsonElement extraElement = object.get("extra");
        if (Validate.notNull(extraElement)) {
            List<TextComponent> componentList = new ArrayList<>();
            extraElement.getAsJsonArray().forEach(e -> componentList.add(test(e)));
            builder.append(componentList);
        }

        return builder.build();
    }

    private void applyBoolean(JsonObject object, String name, TextComponent.Builder builder) {
        JsonElement element = object.get(name);
        if (Validate.notNull(element) && Validate.matchesBoolean(element.getAsString())) {
            builder.decoration(TextDecoration.valueOf(name.toUpperCase(Locale.ROOT)), element.getAsBoolean());
        }
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

    @SubCommand("place")
    public void testPlaceBlock(CommandSender commandSender) {
        if (!(commandSender instanceof Player player)) return;
        Pool.get(BlockPlacer.class).impl().placeNoteBlock(player.getWorld(), 10, 1145, -60, 1145);
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