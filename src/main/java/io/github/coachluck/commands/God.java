package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getLogger;

public class God implements CommandExecutor {
    private final EssentialServer plugin;
    public God(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("god")) {
            if (sender instanceof Player) {
            Player player = (Player) sender;
            player.setInvulnerable(true);
            player.sendMessage(ChatColor.AQUA + "God mode enabled.");
                player.setInvulnerable(true);
                player.sendTitle(ChatColor.GOLD + "God Mode", ChatColor.GRAY + "Has been enabled.", 3, 6, 3);
            return true;
            } else {
                getLogger().info("You need to be a player to execute this command!");
            }
        }
        return false;
    }
}
