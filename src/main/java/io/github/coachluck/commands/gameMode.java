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
        boolean enableMsg = plugin.getConfig().getBoolean("gamemode.message-enable");
        String gOtherMsg = plugin.getConfig().getString("gamemode.message-others");
        String noPlayer = "&cSpecified player could not be found!";

        if(sender instanceof Player && sender.hasPermission("essentialserver.gamemode")) {
            Player p = (Player) sender;
            if (args.length == 0) msg(p, "&cPlease specify a gamemode! &a/gamemode [&bmode&a]&c.");
            else if (args.length == 1) changeGM(args[0], p, sender);
            else if (args.length == 2 && sender.hasPermission("essentialserver.gamemode.others")) {
                if (Bukkit.getPlayerExact(args[1]) != null) {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (enableMsg && !p.getDisplayName().equalsIgnoreCase(target.getDisplayName()))
                        msg(sender, gOtherMsg
                                .replaceAll("%player%", target.getDisplayName())
                                .replaceAll("%mode%", args[0].toLowerCase()));
                    changeGM(args[0], target, sender);
                } else msg(sender, noPlayer);
            } else badUse(sender);
        } else if (sender instanceof ConsoleCommandSender){
            if(args.length != 2) badUse(sender);
            else {
                if (Bukkit.getPlayerExact(args[1]) != null) {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    changeGM(args[0], target, sender);
                    if (enableMsg)
                        msg(sender, gOtherMsg
                            .replaceAll("%player%", target.getDisplayName())
                            .replaceAll("%mode%", args[0].toLowerCase()));
                } else msg(sender, noPlayer);
            }
        }
    return true;
    }

    /**
     * Changes the gamemode of the player w the args
     * @param GAMEMODE the gamemode to switch too
     * @param target the player to change the gamemode of
     * @param s the player changing the gamemode
     */
    private void changeGM(String GAMEMODE, Player target, CommandSender s) {
        boolean enableMsg = plugin.getConfig().getBoolean("gamemode.message-enable");
        String gMsg = plugin.getConfig().getString("gamemode.message");
        if (target != null) {
            if (GAMEMODE.equalsIgnoreCase("survival") || GAMEMODE.equalsIgnoreCase("0")) {
                target.setGameMode(GameMode.SURVIVAL);
                if (enableMsg)
                    msg(target, gMsg.replaceAll("%mode%", "Survival"));
            }
            else if (GAMEMODE.equalsIgnoreCase("creative") || GAMEMODE.equalsIgnoreCase("1")) {
                target.setGameMode(GameMode.CREATIVE);
                if (enableMsg)
                    msg(target, gMsg.replaceAll("%mode%", "Creative"));
            }
            else if (GAMEMODE.equalsIgnoreCase("adventure") || GAMEMODE.equalsIgnoreCase("2")) {
                target.setGameMode(GameMode.ADVENTURE);
                if (enableMsg)
                    msg(target, gMsg.replaceAll("%mode%", "Adventure"));
            } else badUse(s);
        }
    }

    /**
     * Send the bad usage message
     * @param s the sender of the command
     */
    private void badUse(CommandSender s) {
        if(s.hasPermission("essentialserver.tp.others")) msg(s, "&cIncorrect usage! Try &a/gamemode [&bmode&a] <&bplayer&a>");
        else msg(s, "&cIncorrect usage! Try &a/gamemode [&bmode&a]");
    }

}
