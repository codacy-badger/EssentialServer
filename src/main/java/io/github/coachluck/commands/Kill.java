package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static io.github.coachluck.utils.ChatUtils.*;

public class Kill implements CommandExecutor {
    private final EssentialServer plugin;

    public Kill(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String killMsg = plugin.getConfig().getString("kill.message");
        String killOtherMsg = plugin.getConfig().getString("kill.others-message");
        String suicideMsg = plugin.getConfig().getString("kill.suicide-message");
        boolean enableMsg = plugin.getConfig().getBoolean("kill.message-enable");

        if (args.length == 0) {
            if (sender instanceof Player && sender.hasPermission("essentialserver.kill")) {
                Player player = (Player) sender;
                if (enableMsg) {
                    msg(player, format(suicideMsg));
                }
                player.setHealth(0);
            } else {
                msg(sender, format("&cYou must be a player to execute this command!"));
            }
        } else if (args.length == 1 && sender.hasPermission("essentialserver.kill.others")) {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target != null) {
                target.setHealth(0);
                if (enableMsg) {
                    msg(target, format(killMsg));
                    msg(sender, format(killOtherMsg.replace("%player%", target.getDisplayName())));
                }
            } else {
                msg(sender, format("&cThe specified player could not be found!"));
            }
        } else if (args.length > 1) {
            msg(sender, format("&cToo many arguments! Try /kill <player> or /kill."));
        }
        return true;
    }
}