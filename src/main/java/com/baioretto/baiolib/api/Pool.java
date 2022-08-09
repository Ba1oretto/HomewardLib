package com.baioretto.baiolib.api;

import com.baioretto.baiolib.BaioLib;
import com.baioretto.baiolib.api.extension.meta.PaperItemMeta;
import com.baioretto.baiolib.api.extension.sender.command.PaperConsoleCommandSender;
import com.baioretto.baiolib.api.extension.sender.player.PaperPlayer;
import com.baioretto.baiolib.api.extension.stack.PaperItemStack;
import com.baioretto.baiolib.api.packet.PacketUtil;
import com.baioretto.baiolib.api.persistence.PersistenceUtil;
import com.baioretto.baiolib.api.player.PlayerUtil;
import com.baioretto.baiolib.util.ReflectionUtil;
import com.baioretto.baiolib.util.ServerUtil;
import com.google.common.base.CaseFormat;
import lombok.Getter;

import java.util.HashMap;

/**
 * The {@code pool} class stores lots of class instance.
 *
 * @author baioretto
 * @since 1.1.0
 */
public class Pool {
    private static volatile PacketUtil packetUtil;
    private static volatile PaperItemStack paperItemStack;
    private static volatile PaperItemMeta paperItemMeta;
    private static volatile PlayerUtil playerUtil;
    private static volatile PaperPlayer paperPlayer;
    private static volatile PaperConsoleCommandSender paperConsoleCommandSender;
    private static volatile PersistenceUtil persistenceUtil;

    /**
     * Get an instance of generic type T.
     *
     * @param of  the class which you want to get an instance of it
     * @param <T> a generic type extend from {@link AbstractUtil}
     * @return the instance of generic type T
     */
    public static <T extends AbstractUtil<?>> T get(Class<T> of) {
        return getInstance(of);
    }

    private static <T> T getInstance(Class<T> of) {
        return ReflectionUtil.getSingletonField(Pool.class, of, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, of.getSimpleName()));
    }

    /**
     * Stores the classes under the api package.
     */
    @Getter
    @SuppressWarnings("rawtypes")
    private static final HashMap<String, Class<? extends AbstractUtil>> ABSTRACT_API_CLASSES = new HashMap<>();

    static {
        ServerUtil.getReflections(BaioLib.class.getPackageName() + ".api").getSubTypesOf(AbstractUtil.class).forEach(clazz -> {
            String className = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, clazz.getSimpleName());
            ABSTRACT_API_CLASSES.put(className, clazz);
        });
    }
}
