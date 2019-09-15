package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static io.github.coachluck.utils.ChatUtils.*;

public class Smite implements CommandExecutor {
    private final EssentialServer plugin;

    public Smite(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String smiteMsg = plugin.getConfig().getString("smite.message");
        String smiteOtherMsg = plugin.getConfig().getString("smite.others-message");
        String selfMsg = plugin.getConfig().getString("smite.self-message");
        boolean enableMsg = plugin.getConfig().getBoolean("smite.message-enable");

        if (args.length == 0 && sender.hasPermission("essentialserver.smite")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Location pLoc = player.getLocation();
                player.getWorld().strikeLightning(pLoc);

                if (enableMsg) {
                    msg(player, format(selfMsg));
                }
            } else {
                msg(sender, format("&cYou must be a player to execute this command!"));
            }
        } else if (args.length == 1 && sender.hasPermission("essentialserver.smite.others")) {
            Player target = Bukkit.getPlayerExact(args[0]);
            try {
                Location tLoc = target.getLocation();
                target.getWorld().strikeLightning(tLoc);
                if (enableMsg) {
                    if(sender instanceof Player) {
                        Player p = (Player) sender;
                        if (!p.getDisplayName().equalsIgnoreCase(target.getDisplayName())) {
                            msg(target, format(smiteMsg));
                            msg(p, format(smiteOtherMsg.replace("%player%", target.getDisplayName())));
                        } else {
                            msg(p, format(selfMsg));
                        }
                    } else if (sender instanceof ConsoleCommandSender) {
                        msg(target, format(smiteMsg));
                        msg(sender, format(smiteOtherMsg.replace("%player%", target.getDisplayName())));
                    }
                }
            } catch (NullPointerException e) {
                msg(sender, format("&cThe specified player could not be found!"));
            }
        } else if (args.length > 1) {
            msg(sender, format("&cToo many arguments! Try /smite <player> or /smite."));
        }
        return true;
    }
}