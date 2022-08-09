package com.baioretto.baiolib.dev.listener;

import com.baioretto.baiolib.BaioLibConfig;
import com.baioretto.baiolib.util.ServerUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class CommandListener implements Listener {
    @EventHandler
    public void onReload(ServerCommandEvent event) {
        if (!event.getCommand().equalsIgnoreCase(BaioLibConfig.RELOAD)) return;
        event.setCancelled(true);
        // handle reload
        ServerUtil.reload(event.getSender());
    }

    @EventHandler
    public void onReload(PlayerCommandPreprocessEvent event) {
        if (!event.getMessage().startsWith("/reload")) return;
        event.setCancelled(true);
        ServerUtil.reload(event.getPlayer());
    }
}
