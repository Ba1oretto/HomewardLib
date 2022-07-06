package com.baioretto.baiolib.api.block.placer;

import com.baioretto.baiolib.api.Wrapper;

public class BlockPlacer {
    public static IBlockPlacer get() {
        return getInstance().blockPlacer;
    }

    private final IBlockPlacer blockPlacer;

    private BlockPlacer() {
        blockPlacer = Wrapper.getClassInstance(IBlockPlacer.class);
    }

    private static volatile BlockPlacer instance;

    private static BlockPlacer getInstance() {
        if (instance == null)
            synchronized (BlockPlacer.class) {
                if (instance == null) return new BlockPlacer();
            }
        return instance;
    }
}
