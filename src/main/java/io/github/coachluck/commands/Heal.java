package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import static io.github.coachluck.utils.chatUtils.*;
import static org.bukkit.Bukkit.getLogger;

public class Heal implements CommandExecutor {
    private final EssentialServer plugin;
    public Heal(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String healMsg = plugin.getConfig().getString("heal.message");
        String healOtherMsg = plugin.getConfig().getString("heal.other-message");
        boolean enableMsg = plugin.getConfig().getBoolean("heal.message-enable");

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 0 && player.hasPermission("essentialserver.heal")) {
                if (enableMsg) {
                    player.sendMessage(format(healMsg));
                }
                player.setHealth(20);
                player.setFoodLevel(20);
            }else if(args.length == 1){
                Player target = (Bukkit.getPlayerExact(args[0]));
                if(target instanceof Player) {
                    if(player.hasPermission("essentialserver.heal.others")) {
                        target.setHealth(20);
                        target.setFoodLevel(20);
                        if(enableMsg) {
                            target.sendMessage(format(healMsg));
                            player.sendMessage(format(healOtherMsg).replace("%player%", target.getDisplayName()));
                        }
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
                    target.setHealth(20);
                    target.setFoodLevel(20);
                        if(enableMsg) {
                        target.sendMessage(format(healMsg));
                        getLogger().info(logFormat(healOtherMsg).replace("%player%", target.getDisplayName()));
                        }
                }else {
                    getLogger().info(ChatColor.RED + "The specified player could not be found!");
                }
            }
        }
        return true;
    }

}