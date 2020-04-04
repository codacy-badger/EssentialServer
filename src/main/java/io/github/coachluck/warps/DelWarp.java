package io.github.coachluck.warps;

import io.github.coachluck.EssentialServer;
import io.github.coachluck.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DelWarp implements CommandExecutor, TabCompleter {
    EssentialServer plugin;

    public DelWarp(EssentialServer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender.hasPermission("essentialserver.delwarp")) {
            if(args.length != 1) {
                ChatUtils.msg(sender, "&cIncorrect usage: &e/delwarp &b<WarpName>");
                return true;
            } else {
                String warpName = args[0].toLowerCase();
                if(plugin.warpMap.containsKey(warpName)) {
                    plugin.warpFile.removeWarp(warpName);
                    plugin.reloadWarpsMap();
                    ChatUtils.msg(sender, "&7You have &cremoved &7warp: &e" + warpName);
                } else {
                    ChatUtils.msg(sender, "&cCould not find warp &e" + warpName);
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        if(args.length == 1) {
            ArrayList<String> warps = new ArrayList<>(plugin.warpMap.keySet());
            Collections.sort(warps);
            return warps;
        } else {
            return new ArrayList<>();
        }
    }
}
