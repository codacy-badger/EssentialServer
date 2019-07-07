package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import static io.github.coachluck.utils.chatUtils.*;
import static org.bukkit.Bukkit.getLogger;

public class Clear implements CommandExecutor {
    private final EssentialServer plugin;
    public Clear(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String clearMsg = plugin.getConfig().getString("clear.message");
        String clearOtherMsg = plugin.getConfig().getString("clear.others-message");
        String pMsg = plugin.getConfig().getString("permission-message");
        boolean enableMsg = plugin.getConfig().getBoolean("clear.message-enable");

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0 && player.hasPermission("essentialserver.clear")) {
                player.getInventory().clear();
                if(enableMsg) {
                    player.sendMessage(format(clearMsg));
                }
            }
            else if(args.length == 1 && player.hasPermission("essentialserver.clear.others")) {
                Player target = (Bukkit.getPlayerExact(args[0]));
                if (target instanceof Player) {
                    PlayerInventory targetInv = target.getInventory();
                    targetInv.clear();
                    if(enableMsg) {
                        player.sendMessage(format(clearOtherMsg.replace("%player%", target.getDisplayName())));
                        target.sendMessage(format(clearMsg));
                    }
                }else {
                    player.sendMessage(ChatColor.RED + "Specified player could not be found!");
                }
            }
            else if((!(player.hasPermission("essentialserver.clear"))) || (!(player.hasPermission("essentialserver.clear.others"))))
            {
                player.sendMessage(format(pMsg));
            } else {
                return false;
            }
        } else {
            if(args.length == 0) {
                getLogger().info("You must be a player to use this command!");
            } else if (args.length == 1) {
                Player target = (Bukkit.getPlayerExact(args[0]));
                if(target instanceof Player) {
                    PlayerInventory targetInv = target.getInventory();
                    targetInv.clear();
                    if(enableMsg) {
                        getLogger().info(logFormat(clearOtherMsg).replace("%player%", target.getDisplayName()));
                    }
                }else {
                    getLogger().info("Specified player could not be found!");
                }
            }
        }
        return true;
    }
}
