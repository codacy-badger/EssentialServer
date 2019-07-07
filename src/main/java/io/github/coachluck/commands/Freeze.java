package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Freeze implements CommandExecutor {
    EssentialServer plugin;

    public Freeze(EssentialServer plugin) {
        this.plugin = plugin;
    }

    public ArrayList<Player> frozen_players = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 0){
                player.sendMessage(ChatColor.RED + "Incorrect usage! Try /freeze [player]");
            }
            else if(args.length == 1 && player.hasPermission("essentialserver.freeze")) {
                Player target = (Bukkit.getPlayerExact(args[0]));
                freezePlayer(target);
            }
        }



        return true;
    }

    private void freezePlayer(Player player) {
        if(frozen_players.contains(player)) {
            frozen_players.remove(player);

        }else if(!frozen_players.contains(player)) {
            frozen_players.add(player);
        }
    }
}
