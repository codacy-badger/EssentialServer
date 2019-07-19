package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static io.github.coachluck.utils.ChatUtils.*;

public class Fly implements CommandExecutor {
    private final EssentialServer plugin;
    public Fly(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }
    private ArrayList < Player > flying_players = new ArrayList < > ();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String flyOtherOnMsg = plugin.getConfig().getString("fly.other-on-message");
        String flyOtherOffMsg = plugin.getConfig().getString("fly.other-off-message");
        boolean enableMsg = plugin.getConfig().getBoolean("fly.message-enable");

        if (args.length == 0) {
            if (sender instanceof Player && sender.hasPermission("essentialserver.fly")) {
                flightCheck((Player) sender);
            } else {
                msg(sender, format("&cYou must be a player to execute this command!"));
            }
        } else if (args.length == 1 && sender.hasPermission("essentialserver.fly.others")) {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target != null) {
                flightCheck(target);
                if (enableMsg) {
                    if (flying_players.contains(target)) {
                        msg(sender, format(flyOtherOnMsg.replace("%player%", target.getDisplayName())));
                    } else if (!flying_players.contains(target)) {
                        msg(sender, format(flyOtherOffMsg.replace("%player%", target.getDisplayName())));
                    }
                }
            } else {
                msg(sender, format("&cThe specified player could not be found!"));
            }
        } else if (args.length > 1) {
            msg(sender, format("&cToo many arguments! Try /fly <player> or /fly."));
        }
        return true;
    }

    private void flightCheck(Player player) {
        String flyMsg = plugin.getConfig().getString("fly.on-message");
        String flyOffMsg = plugin.getConfig().getString("fly.off-message");
        boolean enableMsg = plugin.getConfig().getBoolean("fly.message-enable"); {
            if (player.hasPermission("essentialserver.fly")) {
                if (flying_players.contains(player)) {
                    flying_players.remove(player);
                    player.setAllowFlight(false);
                    if (enableMsg) {
                        player.sendMessage(format(flyOffMsg.replace("%player%", player.getDisplayName())));
                    }
                } else if (!flying_players.contains(player)) {
                    flying_players.add(player);
                    player.setAllowFlight(true);
                    if (enableMsg) {
                        player.sendMessage(format(flyMsg.replace("%player%", player.getDisplayName())));
                    }
                }
            }
        }
    }
}