package com.baioretto.baiolib.api.block.version;

import com.baioretto.baiolib.api.block.IBlockUtil;
import org.bukkit.block.Barrel;

@SuppressWarnings("unused")
public class v1_18_R2 implements IBlockUtil {
    @Override
    public void playBarrelOpenAnimation(Barrel barrel) {
        barrel.open();
    }

    @Override
    public void playBarrelCloseAnimation(Barrel barrel) {
        barrel.close();
    }
}
