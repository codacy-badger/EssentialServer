package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getLogger;

public class Heal implements CommandExecutor {


    private final EssentialServer plugin;
    public Heal(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String healMsg = plugin.getConfig().getString("heal.message");
        String healOtherMsg = plugin.getConfig().getString("heal.healed-player-message");
        boolean enableMsg = plugin.getConfig().getBoolean("heal.message-enable");
            if (sender instanceof Player && sender.hasPermission("essentialserver.heal.self")) {
                if (args.length > 0) {
                if (sender.hasPermission("essentialserver.heal.self")) {
                    Player player = (Player) sender;
                    player.setHealth(20);
                    player.setFoodLevel(20);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("heal.message")));
                    return true;
                }else if (!(sender.hasPermission("essentialserver.heal"))) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("permission-message")));
                }
                }
            }
         else {
            getLogger().info("You need to be a player to execute this command!");
        }
        return true;
    }

}