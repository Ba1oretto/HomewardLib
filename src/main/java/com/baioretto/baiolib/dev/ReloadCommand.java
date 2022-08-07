package com.baioretto.baiolib.dev;

import com.baioretto.baiolib.util.Util;
import me.mattstudios.mf.annotations.Alias;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;

/**
 * Override default reload command.
 *
 * @since 1.4.0
 * @author baioretto
 */
@Command("reload")
@Alias("r")
public class ReloadCommand extends CommandBase {
    @Default
    @Permission("baiolib.admin.reload")
    public void reload(CommandSender sender) {
        Util.reload(sender);
    }
}
