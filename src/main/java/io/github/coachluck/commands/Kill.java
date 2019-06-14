package io.github.coachluck.commands;

import com.sun.org.apache.xpath.internal.operations.Variable;
import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

import static org.bukkit.Bukkit.getLogger;

public class Kill implements CommandExecutor {
    private final EssentialServer plugin;
    public Kill(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String killMsg = plugin.getConfig().getString("kill.message");
        String killTitle = plugin.getConfig().getString("kill.title");
        String killSubT = plugin.getConfig().getString("kill.sub-title");
        int fade = plugin.getConfig().getInt("kill.fade");
        int stay = plugin.getConfig().getInt("kill.stay");
        boolean enableMsg = plugin.getConfig().getBoolean("kill.message-enable");
        boolean enableTitle = plugin.getConfig().getBoolean("kill.title-enable");

        if (command.getName().equals("kill")) {
            Player player = (Player) sender;
            Player target = (Bukkit.getServer().getPlayer(args[0]));

            if (sender instanceof Player && target == null) {
           //     if (args.length == 0) {

                    player.sendTitle(ChatColor.translateAlternateColorCodes('&', killTitle), ChatColor.translateAlternateColorCodes('&', killSubT),
                            fade, stay, fade);
                    return true;
                //else if(args.length == 0 && target == null) {
               //     target.setHealth(0);
               //     player.sendMessage(ChatColor.AQUA + args[1] + ChatColor.RED + ", has been killed!");
               //     return false;
              //  }else if(args.length > 2) {
               //     player.sendMessage("Too many arguments! Do /kill or /kill [player]");
             //       return false;
                //}
            } else if(!(sender instanceof Player)) {
                getLogger().info("You need to be a player to execute this command!");
            }
        }
        return false;
    }
}
