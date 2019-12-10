package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static io.github.coachluck.utils.ChatUtils.*;

public class Vanish implements CommandExecutor {

    EssentialServer plugin;

    public Vanish(EssentialServer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String vanishOtherMsg = plugin.getConfig().getString("vanish.other-on-message");
        String vanishOtherOffMsg = plugin.getConfig().getString("vanish.other-off-message");
        boolean enableMsg = plugin.getConfig().getBoolean("vanish.message-enable");

        // /vanish
        if(args.length == 0 && sender.hasPermission("essentialserver.vanish")) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                vanishCheck(p.getUniqueId());
            } else {
                logMsg("&cYou must be a player to execute this command!");
            }
        }

        // /vanish <player>
        else if (args.length == 1 && sender.hasPermission("essentialserver.vanish.others")) {
            Player tP = Bukkit.getPlayerExact(args[0]);
            UUID target = tP.getUniqueId();
            try {
                vanishCheck(target);
                if(enableMsg) {
                    if (plugin.vanish_players.contains(target)) {
                        msg(sender, vanishOtherMsg.replaceAll("%player%", tP.getDisplayName()));
                    }
                    else if (!plugin.vanish_players.contains(target)) {
                        msg(sender, vanishOtherOffMsg.replaceAll("%player%", tP.getDisplayName()));
                    }
                }
            } catch (NullPointerException e) {
                msg(sender, format("&cThe specified player could not be found!"));
            }
        }

        //excess args handler
        else if (args.length > 1) {
            msg(sender, "&cToo many arguments! Try /vanish <player> or /vanish.");
        }
        return true;
    }

    private void vanishCheck(UUID pUUID) {
        Player player = Bukkit.getPlayer(pUUID);
        String vanishMsg = plugin.getConfig().getString("vanish.on-message");
        String vanishOffMsg = plugin.getConfig().getString("vanish.off-message");
        boolean enableMsg = plugin.getConfig().getBoolean("vanish.message-enable");{ if (player.hasPermission("essentialserver.vanish")) {
            if (plugin.vanish_players.contains(pUUID)) {
                for(Player people : Bukkit.getOnlinePlayers()) {
                    people.showPlayer(plugin, player);
                }
                plugin.vanish_players.remove(pUUID);

                if (enableMsg) {
                    msg(player, vanishOffMsg.replace("%player%", player.getDisplayName()));
                }
            } else if (!plugin.vanish_players.contains(pUUID)) {
                for(Player people : Bukkit.getOnlinePlayers()) {
                    people.hidePlayer(plugin, player);
                }
                plugin.vanish_players.add(pUUID);
                player.setInvulnerable(true);
                if (enableMsg) {
                    msg(player, vanishMsg.replace("%player%", player.getDisplayName()));
                }
            }
        }
        }
    }
}
