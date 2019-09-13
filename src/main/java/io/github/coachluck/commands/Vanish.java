package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static io.github.coachluck.utils.ChatUtils.format;
import static io.github.coachluck.utils.ChatUtils.msg;

public class Vanish implements CommandExecutor {

    EssentialServer plugin;

    public Vanish(EssentialServer plugin) {
        this.plugin = plugin;
    }

    public ArrayList<Player> vanish_players = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String vanishOtherMsg = plugin.getConfig().getString("vanish.others-on-message");
        String vanishOtherOffMsg = plugin.getConfig().getString("vanish.others-off-message");
        boolean enableMsg = plugin.getConfig().getBoolean("vanish.message-enable");

        // /vanish
        if(args.length == 0 && sender.hasPermission("essentialserver.vanish")) {
            if(sender instanceof Player) {
                vanishCheck((Player) sender);
            } else {
                msg(sender, format("&cYou must be a player to execute this command!"));
            }
        }

        // /vanish <player>
        else if (args.length == 1 && sender.hasPermission("essentialserver.vanish.others")) {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target != null) {
                vanishCheck(target);
                if(enableMsg) {
                    if (vanish_players.contains(target)) {
                        msg(sender, format(vanishOtherMsg.replace("%player%", target.getDisplayName())));
                    }
                    else if (!vanish_players.contains(target)) {
                        msg(sender, format(vanishOtherOffMsg.replace("%player%", target.getDisplayName())));
                    }
                }
            } else {
                msg(sender, format("&cThe specified player could not be found!"));
            }
        }

        //excess args handler
        else if (args.length > 1) {
            msg(sender, format("&cToo many arguments! Try /vanish <player> or /vanish."));
        }
        return true;
    }

    private void vanishCheck(Player player) {
        String vanishMsg = plugin.getConfig().getString("vanish.on-message");
        String vanishOffMsg = plugin.getConfig().getString("vanish.off-message");
        boolean enableMsg = plugin.getConfig().getBoolean("vanish.message-enable");{ if (player.hasPermission("essentialserver.vanish")) {
            if (vanish_players.contains(player)) {
                for(Player people : Bukkit.getOnlinePlayers()) {
                    people.showPlayer(plugin, player);
                }
                vanish_players.remove(player);

                if (enableMsg) {
                    msg(player, format(vanishOffMsg.replace("%player%", player.getDisplayName())));
                }
            } else if (!vanish_players.contains(player)) {
                for(Player people : Bukkit.getOnlinePlayers()) {
                    people.hidePlayer(plugin, player);
                }
                vanish_players.add(player);
                player.setInvulnerable(true);
                if (enableMsg) {
                    msg(player, format(vanishMsg.replace("%player%", player.getDisplayName())));
                }
            }
        }
        }
    }
}
