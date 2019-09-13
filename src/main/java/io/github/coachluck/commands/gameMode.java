package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;


import static io.github.coachluck.utils.ChatUtils.*;

public class gameMode implements CommandExecutor {
    private final EssentialServer plugin;

    public gameMode(EssentialServer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        boolean enableMsg = plugin.getConfig().getBoolean("heal.message-enable");
        String gOtherMsg = plugin.getConfig().getString("gamemode.message-others");
        String noPlayer = "&cSpecified player could not be found!";

        if(sender instanceof Player && sender.hasPermission("essentialserver.gamemode")) {
            Player p = (Player) sender;
            if (args.length == 0) {
               msg(p, "&cPlease specify a gamemode! &a/gamemode [&bmode&a]&c.");
            }
            else if (args.length == 1) {
                changeGM(args[0], p, sender);
            }
            else if (args.length == 2 && sender.hasPermission("essentialserver.gamemode.others")) {
                if (Bukkit.getPlayerExact(args[1]) != null) {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    changeGM(args[0], target, sender);
                    if (enableMsg && !p.getDisplayName().equalsIgnoreCase(target.getDisplayName())) {
                        msg(sender, format(gOtherMsg).replaceAll("%player%", target.getDisplayName()));
                    }
                } else {
                    msg(sender, format(noPlayer));
                }
            }
            else {
                    badUse(sender);
                }

        } else if (sender instanceof ConsoleCommandSender){
            if(args.length != 2) {
                badUse(sender);
            }
            else {
                if (Bukkit.getPlayerExact(args[1]) != null) {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    changeGM(args[0], target, sender);
                    if (enableMsg) {
                        msg(sender, format(gOtherMsg).replaceAll("%player%", target.getDisplayName()));
                    }
                }
                else {
                    msg(sender, format(noPlayer));
                }
            }
        }
    return true;
    }

    //handles the gamemode change, take args of the command and player to change the gamemode of
    private void changeGM(String args, Player target, CommandSender s) {
        boolean enableMsg = plugin.getConfig().getBoolean("heal.message-enable");
        String gMsg = plugin.getConfig().getString("gamemode.message");
        if (target != null) {
            if (args.equalsIgnoreCase("survival") || args.equalsIgnoreCase("0")) {
                target.setGameMode(GameMode.SURVIVAL);
                if (enableMsg) {
                    msg(target, format(gMsg).replaceAll("%mode%", "Survival"));
                }
            }
            else if (args.equalsIgnoreCase("creative") || args.equalsIgnoreCase("1")) {
                target.setGameMode(GameMode.CREATIVE);
                if (enableMsg) {
                    msg(target, format(gMsg).replaceAll("%mode%", "Creative"));
                }
            }
            else if (args.equalsIgnoreCase("adventure") || args.equalsIgnoreCase("2")) {
                target.setGameMode(GameMode.ADVENTURE);
                if (enableMsg) {
                    msg(target, format(gMsg).replaceAll("%mode%", "Adventure"));
                }
            } else {
                badUse(s);
            }
        }
    }
    private void badUse(CommandSender s) {
        msg(s, format("&cIncorrect usage! Try &a/gamemode [&bmode&a] <&bplayer&a>"));
    }

}
