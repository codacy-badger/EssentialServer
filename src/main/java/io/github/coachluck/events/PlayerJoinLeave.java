package io.github.coachluck.events;

import io.github.coachluck.EssentialServer;
import io.github.coachluck.commands.Vanish;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static io.github.coachluck.utils.ChatUtils.format;
import static org.bukkit.Bukkit.getPlayer;

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
                    TextComponent mainComponent = new TextComponent(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "Essential Server" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " is out of date! Get the new version ");
                    TextComponent subComponent = new TextComponent("here");
                    subComponent.setColor(ChatColor.AQUA);
                    subComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GOLD + "Click Me!").create()));
                    subComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/essential-server.71299/"));
                    mainComponent.addExtra(subComponent);
                    player.spigot().sendMessage(mainComponent);
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