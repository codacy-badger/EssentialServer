package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import io.github.coachluck.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                if (enableMsg) ChatUtils.msg(player, feedMsg);
                int foodLvL = player.getFoodLevel();
                int finalAmt = foodLvL + amt;
                player.setFoodLevel(finalAmt);
            } else ChatUtils.logMsg("&cYou must be a player to execute this command!");
        } else if (args.length == 1 && sender.hasPermission("essentialserver.feed.others")) {
            Player target = Bukkit.getPlayerExact(args[0]);
            if(target == null) {
                ChatUtils.msg(sender, "&cThe specified player could not be found!");
                return true;
            }
            int foodLvL = target.getFoodLevel();
            int finalAmt = foodLvL + amt;
            target.setFoodLevel(finalAmt);
            ChatUtils.sendMessages(sender, feedMsg, feedOtherMsg, feedMsg, enableMsg, target);
        } else if (args.length > 1) {
            ChatUtils.msg(sender, "&cToo many arguments! Try /feed <player> or /feed.");
        }
        return true;
    }
}