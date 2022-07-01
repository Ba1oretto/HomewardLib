package com.baioretto.homewardlib.api.blockutil;

import com.baioretto.homewardlib.api.Wrapper;

@SuppressWarnings("unused")
public class BlockUtil {
    /**
     * @return IBlockUtil
     */
    public static IBlockUtil impl() {
        return getInstance().blockUtil;
    }

    private final IBlockUtil blockUtil;

    private BlockUtil() {
        blockUtil = Wrapper.getClassInstance(IBlockUtil.class, this);
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
