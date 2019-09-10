package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static io.github.coachluck.utils.ChatUtils.msg;
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
                        msg(player,  "&7-=-= &bEssential Server Help &61/4&7 -=-=");
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
