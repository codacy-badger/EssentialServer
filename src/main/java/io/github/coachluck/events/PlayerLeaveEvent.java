package io.github.coachluck.events;

import io.github.coachluck.EssentialServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static io.github.coachluck.utils.ChatUtils.format;

public class PlayerLeaveEvent implements Listener {
    EssentialServer plugin;
    public PlayerLeaveEvent(EssentialServer plugin) {
        this.plugin = plugin;
    }

    boolean enableJoinLeave = plugin.getConfig().getBoolean("server.enable-custom-join-leave-message");

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent q){
        if(enableJoinLeave) {
            Player player = q.getPlayer();
            String leaveMsg = plugin.getConfig().getString("server.leave-message");
            q.setQuitMessage(format(leaveMsg.replace("%player%", player.getDisplayName())));
        }
    }
}
