package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static io.github.coachluck.utils.ChatUtils.*;

public class Feed implements CommandExecutor {
    private final EssentialServer plugin;
    public Feed(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin

    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String feedMsg = plugin.getConfig().getString("feed.message");
        String feedOtherMsg = plugin.getConfig().getString("feed.other-message");
        boolean enableMsg = plugin.getConfig().getBoolean("feed.message-enable");

        if (args.length == 0) {
            if (sender instanceof Player && sender.hasPermission("essentialserver.feed")) {
                Player player = (Player) sender;
                if (enableMsg) {
                    msg(player, format(feedMsg));
                }
                player.setFoodLevel(20);
            } else {
                msg(sender, format("&cYou must be a player to execute this command!"));
            }
        } else if (args.length == 1 && sender.hasPermission("essentialserver.feed.others")) {
            Player target = Bukkit.getPlayerExact(args[0]);
           try {
                target.setFoodLevel(20);
                if (enableMsg) {
                    if(sender instanceof Player) {
                        Player p = (Player) sender;
                        if (!p.getDisplayName().equalsIgnoreCase(target.getDisplayName())) {
                            msg(target, format(feedMsg));
                            msg(p, format(feedOtherMsg.replace("%player%", target.getDisplayName())));
                        } else {
                            msg(p, format(feedMsg));
                        }
                    } else if (sender instanceof ConsoleCommandSender) {
                        msg(target, format(feedMsg));
                        msg(sender, format(feedOtherMsg.replace("%player%", target.getDisplayName())));
                    }
                }
            } catch (NullPointerException e) {
                msg(sender, format("&cThe specified player could not be found!"));
            }
        } else if (args.length > 1) {
            msg(sender, format("&cToo many arguments! Try /feed <player> or /feed."));
        }
        return true;
    }

}