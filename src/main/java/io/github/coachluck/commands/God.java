package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static io.github.coachluck.utils.ChatUtils.*;

public class God implements CommandExecutor {
    private final EssentialServer plugin;
    public God(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }

    private ArrayList<Player> god_players = new ArrayList<>();
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String godOtherMsg = plugin.getConfig().getString("god.others-on-message");
        String godOtherOffMsg = plugin.getConfig().getString("god.others-off-message");
        boolean enableMsg = plugin.getConfig().getBoolean("god.message-enable");

        // /god
        if(args.length == 0 && sender.hasPermission("essentialserver.god")) {
            if(sender instanceof Player) {
                godCheck((Player) sender);
            } else {
                msg(sender, format("&cYou must be a player to execute this command!"));
            }
        }

        // /god <player>
        else if (args.length == 1 && sender.hasPermission("essentialserver.god.others")) {
            Player target = Bukkit.getPlayerExact(args[0]);
            try {
                godCheck(target);
                if(enableMsg) {
                    if (god_players.contains(target)) {

                        msg(sender, godOtherMsg.replace("%player%", target.getDisplayName()));
                    }
                    else if (!god_players.contains(target)) {
                        msg(sender, godOtherOffMsg.replace("%player%", target.getDisplayName()));
                    }
                }
            } catch (NullPointerException e) {
                msg(sender, "&cThe specified player could not be found!");
            }
        }

        //excess args handler
        else if (args.length > 1) {
            msg(sender, "&cToo many arguments! Try /god <player> or /god.");
        }
        return true;
    }

    private void godCheck(Player player) {
        String godMsg = plugin.getConfig().getString("god.on-message");
        String godOffMsg = plugin.getConfig().getString("god.off-message");
        boolean enableMsg = plugin.getConfig().getBoolean("god.message-enable");
        if (god_players.contains(player)) {
            god_players.remove(player);
            player.setInvulnerable(false);
            if (enableMsg) {
                msg(player, godOffMsg.replace("%player%", player.getDisplayName()));
            }
        } else if (!god_players.contains(player)) {
            god_players.add(player);
            player.setInvulnerable(true);
            if (enableMsg) {
                msg(player, godMsg.replace("%player%", player.getDisplayName()));
            }
        }
    }
}
