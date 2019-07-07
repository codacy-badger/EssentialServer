package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static io.github.coachluck.Utils.*;
import static org.bukkit.Bukkit.*;

public class Fly implements CommandExecutor {
    private final EssentialServer plugin;
    public Fly(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }
    private ArrayList<Player> flying_players = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            String pMsg = plugin.getConfig().getString("permission-message");
            String flyOtherOnMsg = plugin.getConfig().getString("fly.other-on-message");
            String flyOtherOffMsg = plugin.getConfig().getString("fly.other-off-message");
            String flyMsg = plugin.getConfig().getString("fly.on-message");
            String flyOffMsg = plugin.getConfig().getString("fly.off-message");
            boolean enableMsg = plugin.getConfig().getBoolean("fly.message-enable");

            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    flightCheck(player);
                } else if (args.length == 1 && player.hasPermission("essentialserver.fly.others")) {
                    Player target = (getPlayerExact(args[0]));
                    if (target instanceof Player) {
                        flightCheck(target);
                        if (flying_players.contains(target)) {
                            player.sendMessage(format(flyOtherOnMsg.replace("%player%", target.getDisplayName())));
                        } else if (!flying_players.contains(target)) {
                            player.sendMessage(format(flyOtherOffMsg.replace("%player%", target.getDisplayName())));
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "The specified player could not be found!");
                    }
                } else if ((!(player.hasPermission("essentialserver.fly"))) ||
                        (!(player.hasPermission("essentialserver.fly.others")))) {
                    player.sendMessage(format(pMsg));
                } else {
                    player.sendMessage(ChatColor.RED + "The specified player could not be found!");
                }
            } else {
                if (args.length == 0) {
                    getLogger().info("You must be a player to use this command. Try /fly [player].");
                } else if (args.length == 1) {
                    Player target = (getPlayerExact(args[0]));
                    if (flying_players.contains(target)) {
                        if (enableMsg) {
                            getLogger().info(logFormat(flyOtherOffMsg.replace("%player%", target.getDisplayName())));
                            target.sendMessage(format(flyOffMsg));
                        }
                        target.setAllowFlight(false);
                        flying_players.remove(target);
                    } else if (!flying_players.contains(target)) {
                        if (enableMsg) {
                            getLogger().info(logFormat(flyOtherOnMsg.replace("%player%", target.getDisplayName())));
                            target.sendMessage(format(flyMsg));
                        }
                        target.setAllowFlight(true);
                        flying_players.add(target);
                    }
                }
            }
            return true;
        }

        private void flightCheck(Player player) {
            String flyMsg = plugin.getConfig().getString("fly.on-message");
            String flyOffMsg = plugin.getConfig().getString("fly.off-message");
            boolean enableMsg = plugin.getConfig().getBoolean("fly.message-enable");{
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

