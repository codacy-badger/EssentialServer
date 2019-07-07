package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static io.github.coachluck.Utils.*;
import static org.bukkit.Bukkit.getLogger;

public class Feed implements CommandExecutor {
    private final EssentialServer plugin;
    public Feed(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin

    }
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            String feedMsg = plugin.getConfig().getString("feed.message");
            String feedOtherMsg = plugin.getConfig().getString("feed.other-message");
            boolean enableMsg = plugin.getConfig().getBoolean("feed.message-enable");

            if (sender instanceof Player) {
                Player player = (Player) sender;
                if(args.length == 0 && player.hasPermission("essentialserver.feed")) {
                    if (enableMsg) {
                        player.sendMessage(format(feedMsg));
                    }
                    player.setFoodLevel(20);
                }else if(args.length == 1){
                    Player target = (Bukkit.getPlayerExact(args[0]));
                    if(target instanceof Player) {
                        if(player.hasPermission("essentialserver.feed.others")) {
                            if(enableMsg) {
                                target.sendMessage(format(feedMsg));
                                player.sendMessage(format(feedOtherMsg).replace("%player%", target.getDisplayName()));
                            }
                            target.setFoodLevel(20);
                        }
                    }else {
                        player.sendMessage(ChatColor.RED + "The specified player could not be found!");
                    }
                }
            }else {
                if(args.length == 0) {
                    getLogger().info("You must be a player to use this command!");
                } else if (args.length == 1) {
                    Player target = (Bukkit.getPlayerExact(args[0]));
                    if(target instanceof Player) {
                            if(enableMsg) {
                            target.sendMessage(format(feedMsg));
                            getLogger().info(logFormat(feedOtherMsg).replace("%player%", target.getDisplayName()));
                        }
                        target.setFoodLevel(20);
                    }else {
                        getLogger().info(ChatColor.RED + "The specified player could not be found!");
                    }
                }
            }
            return true;
        }

    }
