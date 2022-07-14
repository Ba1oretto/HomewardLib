package com.baioretto.baiolib.api;

import com.baioretto.baiolib.api.block.placer.BlockPlacer;
import com.baioretto.baiolib.api.block.util.BlockUtil;
import com.baioretto.baiolib.api.extension.meta.PaperItemMeta;
import com.baioretto.baiolib.api.extension.stack.PaperItemStack;
import com.baioretto.baiolib.api.packet.PacketUtil;
import com.baioretto.baiolib.api.player.PlayerUtil;
import com.baioretto.baiolib.util.ReflectionUtil;
import com.google.common.base.CaseFormat;

/**
 * The {@code pool} class stores lots of class instance.
 *
 * @author baioretto
 * @since 1.1.0
 */
@SuppressWarnings("unused")
public class Pool {
    private static volatile PacketUtil packetUtil;
    private static volatile BlockPlacer blockPlacer;
    private static volatile BlockUtil blockUtil;
    private static volatile PaperItemStack paperItemStack;
    private static volatile PaperItemMeta paperItemMeta;
    private static volatile PlayerUtil playerUtil;

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
}
