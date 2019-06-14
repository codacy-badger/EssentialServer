package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static org.bukkit.Bukkit.getLogger;

public class Feed implements CommandExecutor {
    private final EssentialServer plugin;
    public Feed(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin

    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String feedMsg = plugin.getConfig().getString("feed.message");
        String pMsg = plugin.getConfig().getString("permission-message");
        String feedTitle = plugin.getConfig().getString("feed.title");
        String feedSubT = plugin.getConfig().getString("feed.sub-title");
        int stay = plugin.getConfig().getInt("feed.stay");
        int fade = plugin.getConfig().getInt("feed.fade");
        int amount = plugin.getConfig().getInt("feed.amount");
        boolean enableMsg = plugin.getConfig().getBoolean("feed.message-enable");
        boolean enableTitle = plugin.getConfig().getBoolean("feed.title-enable");

        if (command.getName().equals("feed")) {
            if (sender instanceof Player) {
            Player player = (Player) sender;
                if(player.hasPermission("essentialserver.feed")) {
                    if(enableMsg && !enableTitle) {
                        player.setFoodLevel(amount);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', feedMsg));
                        return true;
                    }else if(!enableMsg && enableTitle) {
                        player.setFoodLevel(amount);

                        player.sendTitle(ChatColor.translateAlternateColorCodes('&', feedTitle),
                                ChatColor.translateAlternateColorCodes('&', feedSubT),
                                fade, stay, fade);
                        return true;
                    } else if (enableMsg && enableTitle) {
                        player.setFoodLevel(amount);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', feedMsg));
                        player.sendTitle(ChatColor.translateAlternateColorCodes('&', feedTitle), ChatColor.translateAlternateColorCodes('&', feedSubT), fade, stay, fade);
                        return false;
                    }else {player.setFoodLevel(amount);}
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', pMsg));
                    return false;
                }
            } else {
                getLogger().info("You need to be a player to execute this command!");
                return false;
            }
        }
        return false;
    }
}
