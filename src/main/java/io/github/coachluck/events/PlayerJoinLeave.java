package io.github.coachluck.events;

import io.github.coachluck.EssentialServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static io.github.coachluck.utils.chatUtils.format;

public class PlayerJoinLeave implements Listener {

    EssentialServer plugin;

    public PlayerJoinLeave(EssentialServer plugin) {
        this.plugin = plugin;
    }
    boolean enableJoinLeave = plugin.getConfig().getBoolean("server.enable-custom-join-leave-message");

        @EventHandler
        public void onPlayerJoin (PlayerJoinEvent e){
        if(enableJoinLeave) {
            String joinMsg = plugin.getConfig().getString("server.join-message");
            Player player = e.getPlayer();

            e.setJoinMessage(format(joinMsg.replace("%player%", player.getDisplayName())));
            }
        }

        @EventHandler
        public void onPlayerLeave (PlayerQuitEvent q){
        if(enableJoinLeave) {
            Player player = q.getPlayer();
            String leaveMsg = plugin.getConfig().getString("server.leave-message");

            q.setQuitMessage(format(leaveMsg.replace("%player%", player.getDisplayName())));
            }
        }
    }

