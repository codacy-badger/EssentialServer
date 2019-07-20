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

        if(args.length == 0 && sender.hasPermission("essentialserver.tp")) {

        }
        else if(args.length == 1 && sender.hasPermission("essentialserver.tp")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                Player target = Bukkit.getPlayerExact(args[1]);
                if(target != null) {
                } else {
                    msg(sender, format("&cThe specified player could not be found!"));
                }
            } else {
                msg(sender, format("&cYou must be a player to use this command!"));
            }
        }
        //teleport others
        else if(args.length == 2) {

        }

        return true;
    }

}
