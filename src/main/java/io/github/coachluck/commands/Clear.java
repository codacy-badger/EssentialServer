package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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

        if (args.length == 0) {
            if (sender instanceof Player && sender.hasPermission("essentialserver.clear")) {
                Player player = (Player) sender;
                player.getInventory().clear();
                if (enableMsg) {
                    msg(player, format(clearMsg));
                }
            } else {
                msg(sender, format("&cYou must be a player to execute this command!"));
            }
        } else if (args.length == 1 && sender.hasPermission("essentialserver.clear.others")) {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target != null) {
                target.getInventory().clear();
                if (enableMsg) {
                    msg(target, format(clearMsg));
                    msg(sender, format(clearOtherMsg.replace("%player%", target.getDisplayName())));
                }
            } else {
                msg(sender, format("&cThe specified player could not be found!"));
            }
        } else if (args.length > 1) {
            msg(sender, format("&cToo many arguments! Try /clear <player> or /clear."));
        }
        return true;
    }
}