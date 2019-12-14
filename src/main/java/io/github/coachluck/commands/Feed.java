package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.*;

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
        int amt = plugin.getConfig().getInt("feed.amount");

        if (args.length == 0) {
            if (sender instanceof Player && sender.hasPermission("essentialserver.feed")) {
                Player player = (Player) sender;
                if (enableMsg) msg(player, feedMsg);
                int foodLvL = player.getFoodLevel();
                int finalAmt = foodLvL + amt;
                player.setFoodLevel(finalAmt);
            } else logMsg("&cYou must be a player to execute this command!");
        } else if (args.length == 1 && sender.hasPermission("essentialserver.feed.others")) {
            try {
                Player target = Bukkit.getPlayerExact(args[0]);
                int foodLvL = target.getFoodLevel();
                int finalAmt = foodLvL + amt;
                target.setFoodLevel(finalAmt);
                if (enableMsg) {
                    if (sender instanceof Player) {
                        Player p = (Player) sender;
                        if (!p.getDisplayName().equalsIgnoreCase(target.getDisplayName())) {
                            msg(target, feedMsg);
                            msg(p, feedOtherMsg.replace("%player%", target.getDisplayName()));
                        } else {
                            msg(p, feedMsg);
                        }
                    } else if (sender instanceof ConsoleCommandSender) {
                        msg(target, feedMsg);
                        msg(sender, feedOtherMsg.replace("%player%", target.getDisplayName()));
                    }
                }
            } catch (NullPointerException e) {
                msg(sender, "&cThe specified player could not be found!");
            }
        } else if (args.length > 1) {
            msg(sender, "&cToo many arguments! Try /feed <player> or /feed.");
        }
        return true;
    }
}