package io.github.coachluck.events;

import io.github.coachluck.EssentialServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

import static io.github.coachluck.Utils.format;

public class FreezeEvent implements Listener {

    EssentialServer plugin;

    public FreezeEvent(EssentialServer plugin) {
        this.plugin = plugin;
    }
    public ArrayList<Player> frozen_players;
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (frozen_players.contains(player.getName())) {
            e.setCancelled(true);
            player.sendMessage(format(plugin.getConfig().getString("freeze.on-message")));

        }else if(!frozen_players.contains(player.getName())) {
            e.setCancelled(false);
            player.sendMessage(format(plugin.getConfig().getString("freeze.off-message")));
        }
    }
}
