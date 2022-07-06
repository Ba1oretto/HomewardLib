package com.baioretto.baiolib.api.block.util;

import com.baioretto.baiolib.api.Wrapper;

@SuppressWarnings("unused")
public class BlockUtil {
    public static IBlockUtil get() {
        return getInstance().blockUtil;
    }

    private final IBlockUtil blockUtil;

    private BlockUtil() {
        blockUtil = Wrapper.getClassInstance(IBlockUtil.class);
    }

    private static volatile BlockUtil instance;

    private static BlockUtil getInstance() {
        if (instance == null)
            synchronized (BlockUtil.class) {
                if (instance == null) return new BlockUtil();
            }
        return instance;
    }
}
