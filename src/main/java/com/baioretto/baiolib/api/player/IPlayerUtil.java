package com.baioretto.baiolib.api.player;

import com.baioretto.baiolib.annotation.Instantiable;
import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.Packet;
import org.bukkit.entity.Player;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * The {@code IPlayerUtil} class provides player bound method.
 *
 * @author baioretto
 * @since 1.1.0
 */
@Instantiable
@SuppressWarnings("unused")
public interface IPlayerUtil {
    /**
     * Send {@link Player} a {@link Packet}
     *
     * @param player the player you want to send
     * @param packet the packet
     */
    void send(Player player, Packet<?> packet);

    /**
     *
     * @param playerStream the player stream
     * @param predicate the filter
     * @param packet the packet
     * @param <M> the generic type extend from {@link Player}
     */
    <M extends Player> void sendAll(Stream<M> playerStream, Predicate<M> predicate, Packet<?> packet);
}
