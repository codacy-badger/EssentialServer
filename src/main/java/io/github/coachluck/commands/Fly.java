package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

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

        String flyOtherMsg = plugin.getConfig().getString("fly.others-on-message");
        String flyOFMsg = plugin.getConfig().getString("fly.others-off-message");

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                flyMethod(player);
            } else if (args.length == 1 && player.hasPermission("essentialserver.fly.others")) {
                Player target = (Bukkit.getPlayerExact(args[0]));
                flyMethod(target);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', flyOtherMsg.replace("%player%", target.getDisplayName())));
            } else if((!(player.hasPermission("essentialserver.fly"))) || (!(player.hasPermission("essentialserver.fly.others"))))
            {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', pMsg));
            }
            else {
                player.sendMessage(ChatColor.RED + "The specified player could not be found!");
            }
        }else{
            if(args.length == 0) {
                getLogger().info("You must be a player to use this command. Try /fly [player].");
            } else if(args.length == 1){
                Player target = (Bukkit.getPlayerExact(args[0]));
                flyMethod(target);
                getLogger().info("Flight has been enabled for " + target.getDisplayName());
            }
        }
        return true;
    }

    private void flyMethod(Player player) {
        String flyMsg = plugin.getConfig().getString("fly.on-message");
        String flyOffMsg = plugin.getConfig().getString("fly.off-message");
        boolean enableMsg = plugin.getConfig().getBoolean("fly.message-enable");

        if (player.hasPermission("essentialserver.fly")) {
            if (flying_players.contains(player)) {
                flying_players.remove(player);
                player.setAllowFlight(false);
                if (enableMsg) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', flyOffMsg));
                }
            } else if (!flying_players.contains(player)) {
                flying_players.add(player);
                player.setAllowFlight(true);
                if (enableMsg) {
                    String playerName = player.getDisplayName();
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', flyMsg));
                }
            }
        }
    }

}
