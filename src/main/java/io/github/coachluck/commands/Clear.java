package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static io.github.coachluck.utils.ChatUtils.*;

public class Clear implements CommandExecutor {
    private final EssentialServer plugin;
    public Clear(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
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
                if (enableMsg) msg(player, clearMsg);

            } else logMsg("&cYou must be a player to execute this command!");
        } else if (args.length == 1 && sender.hasPermission("essentialserver.clear.others")) {
            try {
                Player target = Bukkit.getPlayerExact(args[0]);
                target.getInventory().clear();
                if (enableMsg) {
                    if(sender instanceof Player) {
                        Player p = (Player) sender;
                        if (!p.getDisplayName().equalsIgnoreCase(target.getDisplayName())) {
                            msg(target, clearMsg);
                            msg(p, clearOtherMsg.replace("%player%", target.getDisplayName()));
                        } else msg(p, clearMsg);
                    } else if (sender instanceof ConsoleCommandSender) {
                        msg(target, clearMsg);
                        msg(sender, clearOtherMsg.replace("%player%", target.getDisplayName()));
                    }
                }
            }
            catch (NullPointerException e) {
                msg(sender, "&cThe specified player could not be found!");
            }
        } else if (args.length > 1) msg(sender, "&cToo many arguments! Try /clear <player> or /clear.");
        return true;
    }
}