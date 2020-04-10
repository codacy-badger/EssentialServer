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

    private boolean enableMsg;

    public PlayerJoinLeave(EssentialServer plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        enableMsg = plugin.getConfig().getBoolean("enable-message");
        String joinMsg = plugin.getConfig().getString("join-message");
        Player player = e.getPlayer();
        if(!plugin.vanish_players.isEmpty()) {
            for (int i = 0; i < plugin.vanish_players.size(); i++) {
                player.hidePlayer(plugin, Bukkit.getPlayer(plugin.vanish_players.get(i)));
            }
        }
        if(enableMsg) e.setJoinMessage(format(joinMsg.replace("%player%", player.getDisplayName())));
        if(plugin.updateMsg && player.isOp()) {
                JsonMessage message = new JsonMessage()
                        .append(format("&8[&eEssential Server&8]&c is out of date! &7Get the new version ")).save()
                        .append(format("&ehere")).setClickAsURL("https://bit.ly/37eMbW5").setHoverAsTooltip(format("&6Click Me")).save()
                        .append(format("&7!")).save();
                message.send(player);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        enableMsg = plugin.getConfig().getBoolean("enable-message");
        String quitMsg = plugin.getConfig().getString("leave-message");
        if(enableMsg) e.setQuitMessage(format(quitMsg.replace("%player%", e.getPlayer().getDisplayName())));
        if(plugin.vanish_players.contains(e.getPlayer().getUniqueId())) {
            plugin.vanish_players.remove(e.getPlayer().getUniqueId());
            e.getPlayer().setInvulnerable(false);
        }
    }
}
