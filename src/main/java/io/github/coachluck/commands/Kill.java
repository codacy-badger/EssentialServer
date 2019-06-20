package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getLogger;

public class Kill implements CommandExecutor {
    private final EssentialServer plugin;

    public Kill(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String killMsg = plugin.getConfig().getString("kill.message");
        String killOtherMsg = plugin.getConfig().getString("kill.others-message");
        String suicideMsg = plugin.getConfig().getString("kill.suicide-message");
        boolean enableMsg = plugin.getConfig().getBoolean("kill.message-enable");

            if (sender instanceof Player) {
                Player player = (Player) sender;
                if(args.length == 0 && player.hasPermission("essentialserver.kill")) {
                    if (enableMsg) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', suicideMsg));
                    }
                    player.setHealth(0);
                }else if(args.length == 1){
                    Player target = (Bukkit.getPlayerExact(args[0]));
                    if(target instanceof Player) {
                        if(player.hasPermission("essentialserver.kill.others")) {
                            target.setHealth(0);
                            if(enableMsg) {
                                target.sendMessage(ChatColor.translateAlternateColorCodes('&', killMsg));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', killOtherMsg) + target.getDisplayName());
                            }
                        }
                    }else {
                        player.sendMessage(ChatColor.RED + "The specified player could not be found!");
                    }
                }
            }else {
                if(args.length == 0) {
                    getLogger().info(ChatColor.RED + "You must be a player to use this command!");
                } else if (args.length == 1) {
                    Player target = (Bukkit.getPlayerExact(args[0]));
                    if(target instanceof Player) {
                        target.setHealth(0);
                        getLogger().info(ChatColor.translateAlternateColorCodes('&', killOtherMsg) + target.getDisplayName());
                    }else {
                        getLogger().info(ChatColor.RED + "The specified player could not be found!");
                    }
                }
            }
        return true;
    }
}
