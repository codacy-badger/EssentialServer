package io.github.coachluck.events;

import io.github.coachluck.EssentialServer;
import io.github.coachluck.utils.JsonMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static io.github.coachluck.utils.ChatUtils.format;

public class PlayerJoinLeave implements Listener {
    private final EssentialServer plugin;

    private String joinMsg;
    private String quitMsg;
    private boolean enableMsg;

    public PlayerJoinLeave(EssentialServer plugin) {
        this.plugin = plugin;
        joinMsg = plugin.getConfig().getString("join-message");
        quitMsg = plugin.getConfig().getString("leave-message");
        enableMsg = plugin.getConfig().getBoolean("enable-message");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        for(int i = 0; i < plugin.vanish_players.size(); i++) {
            player.hidePlayer(plugin, Bukkit.getPlayer(plugin.vanish_players.get(i)));
        }
        if(enableMsg) e.setJoinMessage(format(joinMsg.replace("%player%", player.getDisplayName())));
        if(plugin.updateMsg) {
            if(player.isOp()) {
                JsonMessage message = new JsonMessage()
                        .append(format("&8[&eEssential Server&8]&c is out of date! Get the new version ")).save()
                        .append(format("&ehere")).setClickAsURL("https://bit.ly/37eMbW5").setHoverAsTooltip(format("&6Click Me")).save()
                        .append(format("&c!")).save();
                message.send(player);
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        if(enableMsg) e.setQuitMessage(format(quitMsg.replace("%player%", e.getPlayer().getDisplayName())));
        if(plugin.vanish_players.contains(e.getPlayer().getUniqueId())) {
            plugin.vanish_players.remove(e.getPlayer().getUniqueId());
            e.getPlayer().setInvulnerable(false);
        }
    }
}
