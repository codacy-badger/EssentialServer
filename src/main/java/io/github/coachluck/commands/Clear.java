package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import io.github.coachluck.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Clear implements CommandExecutor {
    private EssentialServer plugin;

    public Clear(EssentialServer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String clearMsg = plugin.getConfig().getString("clear.message");
        String clearOtherMsg = plugin.getConfig().getString("clear.others-message");
        boolean enableMsg = plugin.getConfig().getBoolean("clear.message-enable");

        if (args.length == 0 && sender.hasPermission("essentialserver.clear")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.getInventory().clear();
                if (enableMsg)
                    ChatUtils.msg(player, clearMsg);
            } else
                ChatUtils.logMsg("&cYou must be a player to execute this command!");
        } else if (args.length == 1 && sender.hasPermission("essentialserver.clear.others")) {
            try {
                Player target = Bukkit.getPlayerExact(args[0]);
                target.getInventory().clear();
                ChatUtils.sendMessages(sender, clearMsg, clearOtherMsg, clearMsg, enableMsg, target);
            }
            catch (NullPointerException e) {
                ChatUtils.msg(sender, "&cThe specified player could not be found!");
            }
        } else if (args.length > 1) ChatUtils.msg(sender, "&cToo many arguments! Try /clear <player> or /clear.");
        return true;
    }
}