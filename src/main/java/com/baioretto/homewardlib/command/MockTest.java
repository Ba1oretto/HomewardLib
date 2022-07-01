package com.baioretto.homewardlib.command;

import com.baioretto.homewardlib.api.blockutil.BlockUtil;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("test")
@SuppressWarnings("unused")
public class MockTest extends CommandBase {
    @SubCommand("setBlock")
    public void testBlock(CommandSender commandSender) {
        if (!(commandSender instanceof Player player)) return;

        Block block = player.getTargetBlockExact(5);

        BlockUtil.impl().setBlock(block, Material.BARREL);
    }
}
