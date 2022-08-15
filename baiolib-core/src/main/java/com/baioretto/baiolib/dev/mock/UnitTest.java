package com.baioretto.baiolib.dev.mock;

import com.baioretto.baiolib.BaioLib;
import com.baioretto.baiolib.api.CachedVersionWrapper;
import com.baioretto.baiolib.api.extension.bukkit.BukkitImpl;
import com.baioretto.baiolib.api.extension.console.ConsoleCommandSenderImpl;
import com.baioretto.baiolib.api.extension.item.ItemStackImpl;
import com.baioretto.baiolib.api.extension.logger.LoggerImpl;
import com.baioretto.baiolib.api.extension.meta.item.ItemMetaImpl;
import com.baioretto.baiolib.api.extension.player.PlayerImpl;
import com.baioretto.baiolib.api.persistence.PersistenceUtil;
import com.baioretto.baiolib.api.player.IPlayerUtil;
import com.baioretto.baiolib.api.player.PlayerUtil;
import com.baioretto.baiolib.util.ComponentSerializer;
import com.baioretto.baiolib.util.ReflectionUtil;
import com.baioretto.baiolib.util.ServerUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.protocol.game.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static net.kyori.adventure.text.Component.*;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@SuppressWarnings("unused")
public class UnitTest {
    void testCreateInventory() {
        BukkitImpl.createInventory(null, 9, text("test"));
    }

    void testConsoleCommandSenderImpl() {
        ConsoleCommandSenderImpl.sendMessage(plugin.getServer().getConsoleSender(), text("test"));
    }

    void testItemStackImpl() {
        ItemStackImpl.editMeta(itemStack, meta -> {
            meta.setDisplayName("test");
            meta.setLore(List.of("test1", "test2"));
        });
        ItemStackImpl.set(itemStack, NamespacedKey.fromString("test", plugin), PersistentDataType.INTEGER, 1);
        Integer test = ItemStackImpl.get(itemStack, NamespacedKey.fromString("test", plugin), PersistentDataType.INTEGER);
        assert Integer.valueOf(1).equals(test);
    }

    void testLoggerImpl() {
        LoggerImpl.warn(plugin.getLogger(), "test");
    }

    void testItemMetaImpl() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        ItemMetaImpl.displayName(itemMeta, text("display name"));
        TextComponent component = (TextComponent) ItemMetaImpl.displayName(itemMeta);
        assert component != null && "display name".equals(component.content());
        ItemMetaImpl.lore(itemMeta, List.of(text("line1"), text("line2")));
        List<Component> lore = ItemMetaImpl.lore(itemMeta);
        assert lore != null && !lore.isEmpty();
    }

    void testPlayerImpl() {
        Bukkit.getOnlinePlayers().forEach(player -> PlayerImpl.sendMessage(player, text("test")));
    }

    void testPersistenceUtil() {
        assert CachedVersionWrapper.of(PersistenceUtil.class).impl().createPersistentDataContainer() != null;
    }

    void testPlayerUtil() {
        IPlayerUtil playerUtil = CachedVersionWrapper.of(PlayerUtil.class).impl();
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        PacketPlayOutChat chatPacket = new PacketPlayOutChat(ComponentSerializer.serializeToMinecraft(text("send()")), ChatMessageType.b, UUID.randomUUID());
        onlinePlayers.forEach(player -> {
            playerUtil.getConnection(player);
            playerUtil.getNetworkManager(player);
            playerUtil.getChannel(player);
            playerUtil.send(player, chatPacket);
        });
        playerUtil.sendAll(onlinePlayers.stream(), Player::isOp, chatPacket);
    }

    public static void doMock() {
        instance.start();
    }

    public void setUp() {
        this.itemStack = new ItemStack(Material.GREEN_WOOL);
    }

    public void cleanUp() {
        this.testCounter.set(0);
        this.failureCounter.set(0);
        this.itemStack = null;
    }

    private void start() {
        setUp();
        List<Method> methods = Arrays.stream(getClass().getDeclaredMethods()).filter(method -> Modifier.toString(method.getModifiers()).isBlank()).toList();
        ServerUtil.sendConsoleMessage(text("unit test: ", YELLOW).append(text("starting", RED)));
        ServerUtil.sendConsoleMessage(text("method(s) will be test: ", YELLOW).append(text(methods.size(), GREEN)));
        methods.forEach(method -> {
            try {
                testCounter.incrementAndGet();
                ReflectionUtil.invoke(method, instance);
            } catch (Exception e) {
                failureCounter.incrementAndGet();
            }
        });
        int testedMethod = testCounter.get();
        int testFailure = failureCounter.get();
        ServerUtil.sendConsoleMessage(text(testedMethod, testedMethod == 0 ? RED : GREEN).append(text(" method(s) tested", YELLOW)));
        ServerUtil.sendConsoleMessage(text(testFailure, testFailure == 0 ? GREEN : RED).append(text(" failure(s)", YELLOW)));
        ServerUtil.sendConsoleMessage(text("unit test: ", YELLOW).append(text("finished", GREEN)));
        cleanUp();
    }

    private ItemStack itemStack;
    private final static UnitTest instance = new UnitTest();
    private final AtomicInteger testCounter = new AtomicInteger(0);
    private final AtomicInteger failureCounter = new AtomicInteger(0);
    private final BaioLib plugin = BaioLib.instance();
}
