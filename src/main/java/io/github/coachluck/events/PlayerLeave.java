package io.github.coachluck.events;

import io.github.coachluck.EssentialServer;
import io.github.coachluck.files.MotdConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static io.github.coachluck.utils.ChatUtils.format;

public class PlayerLeave implements Listener {
    EssentialServer plugin;
    public PlayerLeave(EssentialServer plugin) {
        this.plugin = plugin;
    }
    String leaveMsg = MotdConfig.get().getString("leave-message");

    @EventHandler
    public void onPlayerLeave (PlayerQuitEvent e) {
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            e.setQuitMessage(format(leaveMsg.replace("%player%", player.getDisplayName())));
        }
    }
}
