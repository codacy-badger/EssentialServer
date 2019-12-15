package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

import static io.github.coachluck.utils.ChatUtils.logMsg;
import static io.github.coachluck.utils.ChatUtils.msg;

public class InvSee implements CommandExecutor {
    EssentialServer plugin;

    public InvSee(EssentialServer plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String invSeeMsg = plugin.getConfig().getString("invsee.message");
        boolean enableMsg = plugin.getConfig().getBoolean("invsee.message-enable");
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(label.equalsIgnoreCase("invsee") && sender.hasPermission("essentialserver.invsee")) { // handle invsee
                if(args.length == 0) { // open player inventory
                    msg(p, "&cPlease specify a player. &a/invsee [&bplayer&a]");
                }
                else if(args.length == 1) {
                    try {
                        Player tP = Bukkit.getServer().getPlayer(args[0]);
                        PlayerInventory tInv = tP.getInventory();
                        p.openInventory(tInv);
                        if(enableMsg) msg(p, invSeeMsg.replaceAll("%player%", tP.getDisplayName()));
                    } catch(NullPointerException e) {
                        msg(p, "&cThe specified player could not be found!");
                    }
                }
                else {
                    msg(p, "&cIncorrect usage! Try &a/invsee [&bplayer&a]");
                }
            }
            else if(label.equalsIgnoreCase("enderchest") && sender.hasPermission("essentialserver.enderchest")) {

            }
        }
        else {
            logMsg("&cYou must be a player to execute this command!");
        }
        return true;
    }
}
