package io.github.coachluck.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Event implements Listener {

    @EventHandler
    public void onEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(!player.hasPermission("essentialserver.move")) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You are not permitted to move!");
        }

    }
}
