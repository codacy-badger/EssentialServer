package io.github.coachluck.events;

import io.github.coachluck.EssentialServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


import static io.github.coachluck.utils.ChatUtils.format;

public class PlayerJoinEvent implements Listener {

    EssentialServer plugin;

    public PlayerJoinEvent(EssentialServer plugin) {
        this.plugin = plugin;
    }
    boolean enableJoinLeave = plugin.getConfig().getBoolean("server.enable-custom-join-leave-message");

        @EventHandler
        public void onPlayerJoin (org.bukkit.event.player.PlayerJoinEvent e) {
            if (enableJoinLeave) {
                String joinMsg = plugin.getConfig().getString("server.join-message");
                Player player = e.getPlayer();
                e.setJoinMessage(format(joinMsg.replace("%player%", player.getDisplayName())));
            }
        }
    }

