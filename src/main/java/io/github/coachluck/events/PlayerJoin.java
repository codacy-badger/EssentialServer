package io.github.coachluck.events;

import io.github.coachluck.EssentialServer;
import io.github.coachluck.files.MotdConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static io.github.coachluck.utils.ChatUtils.format;

public class PlayerJoin implements Listener {
    EssentialServer plugin;
    public PlayerJoin(EssentialServer plugin) {
        this.plugin = plugin;
    }

    String joinMsg = MotdConfig.get().getString("join-message");
        @EventHandler
        public void onPlayerJoin (PlayerJoinEvent e) {

            for(Player player : Bukkit.getServer().getOnlinePlayers()) {
                e.setJoinMessage(format(joinMsg.replace("%player%", player.getDisplayName())));
            }
        }
    }

