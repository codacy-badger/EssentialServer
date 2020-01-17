package io.github.coachluck.events;

import io.github.coachluck.EssentialServer;
import io.github.coachluck.commands.Vanish;
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

    public PlayerJoinLeave(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
        joinMsg = plugin.getConfig().getString("join-message");
        quitMsg = plugin.getConfig().getString("leave-message");
    }

    private String joinMsg;
    private String quitMsg;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        for(int i = 0; i < Vanish.vanish_players.size(); i++) {
            player.hidePlayer(plugin, Bukkit.getPlayer(Vanish.vanish_players.get(i)));
        }
        for (Player player1: Bukkit.getServer().getOnlinePlayers()) {
            e.setJoinMessage(format(joinMsg.replace("%player%", player1.getDisplayName())));
        }
        if(plugin.updateMsg) {
            for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                if(p.isOp()) {
                    JsonMessage message = new JsonMessage().append(format("&8[&eEssential Server&8]&c is out of date! Get the new version ")).save().append(format("&ehere")).setClickAsURL("https://bit.ly/37eMbW5").setHoverAsTooltip(format("&6Click Me")).save().append(format("&c!")).save();
                    message.send(p);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        for (Player player: Bukkit.getServer().getOnlinePlayers()) {
            e.setQuitMessage(format(quitMsg.replace("%player%", player.getDisplayName())));
        }
    }

}