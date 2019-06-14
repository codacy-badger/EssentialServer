package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getLogger;

public class esHelp implements CommandExecutor {
    private final EssentialServer plugin;
    public esHelp(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (command.getName().equals("eshelp")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("essentialserver.help")) {
                    player.getPlayer();

                    if (args.length == 0 || args.equals(1)) {
                        player.sendMessage(ChatColor.DARK_GRAY + "-=-= " + ChatColor.AQUA + "Essential Server Help 1/4" + ChatColor.DARK_GRAY + "-=-=");
                        player.sendMessage(ChatColor.GOLD + "/heal {player}" + ChatColor.GRAY + "- Heals the player or yourself if left unspecified");
                        player.sendMessage(ChatColor.GOLD + "123456789123456789123456789");
                        return false;
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("permission-message")));
                        return false;
                    }
                } else {
                    getLogger().info("You need to be a player to execute this command!");
                    return false;
                }
            }

        }
        return false;
    }
}
