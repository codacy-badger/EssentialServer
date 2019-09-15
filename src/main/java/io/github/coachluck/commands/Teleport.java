package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static io.github.coachluck.utils.ChatUtils.format;
import static io.github.coachluck.utils.ChatUtils.msg;

public class Teleport implements CommandExecutor {
    EssentialServer plugin;

    public Teleport(EssentialServer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String tpOtherMsg = plugin.getConfig().getString("teleport.others-message");
        String tpMsg = plugin.getConfig().getString("teleport.message");
        int COOL_DOWN = plugin.getConfig().getInt("teleport.cooldown-time:");
        boolean enableMsg = plugin.getConfig().getBoolean("teleport.message-enable");

        if (sender instanceof Player && sender.hasPermission("essentialserver.tp")){
            Player player = (Player) sender;

            if (args.length == 0) {
                msg(player, format("&cInsufficient arguments! Please try again."));
                msg(player, format("&cTo teleport yourself: /tp <&botherplayer&c>"));
                if (player.hasPermission("essentialserver.tp.others")) {
                    msg(player, format("&cTo teleport others: /tp <&bplayer&c> <&botherplayer&c>"));
                }
            }else if(args.length == 1){
                Player target = Bukkit.getPlayer(args[0]); //Get player from command
                try{
                    if(player.getDisplayName().equalsIgnoreCase(target.getDisplayName())) {
                        msg(player, format("&cYou can't teleport to yourself..."));
                    }
                    else {
                        msg(player, format(tpMsg.replaceAll("%player%", target.getDisplayName())));
                        player.teleport(target.getLocation());
                    }

                }catch (NullPointerException e){
                    msg(player, format("&cThe specified player could not be found!"));
                }
            }else if(args.length == 2 && player.hasPermission("essentialserver.tp.others")){
                Player playerToSend = Bukkit.getPlayer(args[0]);
                Player target = Bukkit.getPlayer(args[1]);
                try{
                    if(playerToSend.getDisplayName().equalsIgnoreCase(target.getDisplayName())) {
                        msg(sender, format("&cDid you really mean to do that? Try again..."));
                    }
                    else {
                        msg(sender, format(tpOtherMsg.replaceAll("%player1%", playerToSend.getDisplayName()).replaceAll("%player2%", target.getDisplayName())));
                        playerToSend.teleport(target.getLocation());
                    }
                }catch (NullPointerException e){
                    msg(player, format("&cThe specified player could not be found!"));
                }
            }
            else if(args.length > 2 && sender.hasPermission("essentialserver.tp")) {
                msg(sender, format("&cToo many arguments! Try /gm"));
            }
        }
        else {
            msg(sender, format("&cYou have to be a player to use this command!"));
        }


        return true;
    }
}

