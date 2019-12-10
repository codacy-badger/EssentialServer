package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

import static io.github.coachluck.utils.ChatUtils.*;

public class Fly implements CommandExecutor {
    private final EssentialServer plugin;
    public Fly(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }
    private ArrayList <UUID> flying_players = new ArrayList < > ();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String flyOtherOnMsg = plugin.getConfig().getString("fly.other-on-message");
        String flyOtherOffMsg = plugin.getConfig().getString("fly.other-off-message");
        boolean enableMsg = plugin.getConfig().getBoolean("fly.message-enable");

        if (args.length == 0 && sender.hasPermission("essentialserver.fly")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                flightCheck(p.getUniqueId());
            } else {
                msg(sender, "&cYou must be a player to execute this command!");
            }
        } else if (args.length == 1 && sender.hasPermission("essentialserver.fly.others")) {
            Player tP = Bukkit.getPlayerExact(args[0]);
            UUID target = tP.getUniqueId();
            try {
                flightCheck(target);
                if (enableMsg) {
                    if (flying_players.contains(target)) {
                        msg(sender, flyOtherOnMsg.replace("%player%", tP.getDisplayName()));
                    } else if (!flying_players.contains(target)) {
                        msg(sender, flyOtherOffMsg.replace("%player%", tP.getDisplayName()));
                    }
                }
            } catch (NullPointerException e) {
                msg(sender, "&cThe specified player could not be found!");
            }
        } else if (args.length > 1) {
            msg(sender, "&cToo many arguments! Try /fly <player> or /fly.");
        }
        return true;
    }

    private void flightCheck(UUID pUUID) {
        Player player = Bukkit.getPlayer(pUUID);
        String flyMsg = plugin.getConfig().getString("fly.on-message");
        String flyOffMsg = plugin.getConfig().getString("fly.off-message");
        boolean enableMsg = plugin.getConfig().getBoolean("fly.message-enable");
        if (flying_players.contains(pUUID)) {
            flying_players.remove(pUUID);
            player.setAllowFlight(false);
            if (enableMsg) {
                msg(player, flyOffMsg.replace("%player%", player.getDisplayName()));
            }
        } else if (!flying_players.contains(pUUID)) {
            flying_players.add(pUUID);
            player.setAllowFlight(true);
            if (enableMsg) {
                msg(player, flyMsg.replace("%player%", player.getDisplayName()));
            }
        }
    }
}