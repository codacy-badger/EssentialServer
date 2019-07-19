package io.github.coachluck.events;

import io.github.coachluck.EssentialServer;
import io.github.coachluck.files.MotdConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static io.github.coachluck.utils.ChatUtils.format;

public class PlayerJoinLeave implements Listener {
    EssentialServer plugin;
    public PlayerJoinLeave(EssentialServer plugin) {
        this.plugin = plugin;
    }

    String joinMsg = MotdConfig.get().getString("join-message");
    String quitMsg = MotdConfig.get().getString("leave-message");

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        for (Player player: Bukkit.getServer().getOnlinePlayers()) {
            e.setJoinMessage(format(joinMsg.replace("%player%", player.getDisplayName())));
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        for (Player player: Bukkit.getServer().getOnlinePlayers()) {
            e.setQuitMessage(format(quitMsg.replace("%player%", player.getDisplayName())));
        }
    }
}