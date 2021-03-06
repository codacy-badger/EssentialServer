package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import io.github.coachluck.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static io.github.coachluck.utils.ChatUtils.format;
import static io.github.coachluck.utils.ChatUtils.logMsg;

public class SetWarp implements TabCompleter, CommandExecutor {
    private EssentialServer plugin;

    public SetWarp(EssentialServer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length == 1) {
                String warpName = args[0].toLowerCase();
                if(plugin.warpFile.addWarp(warpName, p.getLocation())) {
                    ChatUtils.msg(p, plugin.warpData.getString("messages.create-warp").replaceAll("%warp%", warpName));
                } else {
                    ChatUtils.msg(p, plugin.warpData.getString("messages.warp-already-exists").replaceAll("%warp%", warpName));
                }
                plugin.reloadWarpsMap();

            } else {
                ChatUtils.msg(p, "&cIncorrect usage: &e/setwarp &b<WarpName>");
            }
        } else {
            logMsg("&cYou must be a player to use this command.");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1 && sender instanceof Player) {
            ArrayList<String> name = new ArrayList<>();
            name.add(format("&7<&eWarpName&7>"));
            return name;
        }
        return new ArrayList<>();
    }
}
