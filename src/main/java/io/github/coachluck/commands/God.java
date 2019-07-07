package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static io.github.coachluck.utils.chatUtils.*;
import static org.bukkit.Bukkit.getLogger;

public class God implements CommandExecutor {
    private final EssentialServer plugin;
    public God(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }

    private ArrayList<Player> god_players = new ArrayList<>();
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String pMsg = plugin.getConfig().getString("permission-message");
        String godOtherMsg = plugin.getConfig().getString("god.others-on-message");
        String godOtherOffMsg = plugin.getConfig().getString("god.others-off-message");
        String godMsg = plugin.getConfig().getString("god.on-message");
        String godOffMsg = plugin.getConfig().getString("god.off-message");
        boolean enableMsg = plugin.getConfig().getBoolean("god.message-enable");

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                godCheck(player);
            } else if (args.length == 1 && player.hasPermission("essentialserver.god.others")) {
                Player target = (Bukkit.getPlayerExact(args[0]));
                if(target instanceof Player) {
                    godCheck(target);
                    if(god_players.contains(target)) {
                        player.sendMessage(format(godOtherMsg.replace("%player%", target.getDisplayName())));
                    }
                    else if(!god_players.contains(target)) {
                        player.sendMessage(format(godOtherOffMsg.replace("%player%", target.getDisplayName())));
                    }
                }
                else {
                    player.sendMessage(ChatColor.RED + "The specified player could not be found!");
                }
            }
            else if((!(player.hasPermission("essentialserver.god"))) ||
                    (!(player.hasPermission("essentialserver.god.others"))))
            {
                player.sendMessage(format(pMsg));
            }
            else {
                player.sendMessage(ChatColor.RED + "The specified player could not be found!");
            }
        }else{
            if(args.length == 0) {
                getLogger().info("You must be a player to use this command. Try /god [player].");
            } else if(args.length == 1){
                Player target = (Bukkit.getPlayerExact(args[0]));
                if(god_players.contains(target)){
                    if(enableMsg) {
                        getLogger().info(logFormat(godOtherOffMsg.replace("%player%", target.getDisplayName())));
                        target.sendMessage(format(godOffMsg));
                    }
                    target.setInvulnerable(false);
                    god_players.remove(target);
                }else if(!god_players.contains(target)) {
                    if(enableMsg) {
                        getLogger().info(logFormat(godOtherMsg.replace("%player%", target.getDisplayName())));
                        target.sendMessage(format(godMsg));
                    }
                    target.setInvulnerable(true);
                    god_players.add(target);
                }
            }
        }
        return true;
    }

    private void godCheck(Player player) {
        String godMsg = plugin.getConfig().getString("god.on-message");
        String godOffMsg = plugin.getConfig().getString("god.off-message");
        boolean enableMsg = plugin.getConfig().getBoolean("god.message-enable");{
            if (player.hasPermission("essentialserver.god")) {
                if (god_players.contains(player)) {
                 god_players.remove(player);
                    player.setInvulnerable(false);
                    if (enableMsg) {
                        player.sendMessage(format(godOffMsg.replace("%player%", player.getDisplayName())));
                    }
                } else if (!god_players.contains(player)) {
                    god_players.add(player);
                    player.setInvulnerable(true);
                    if (enableMsg) {
                    player.sendMessage(format(godMsg.replace("%player%", player.getDisplayName())));
                    }
                }
            }

        }

    }
}
