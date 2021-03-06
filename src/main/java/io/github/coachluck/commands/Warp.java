package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import io.github.coachluck.utils.ChatUtils;
import io.github.coachluck.utils.JsonMessage;
import io.github.coachluck.warps.WarpHolder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.github.coachluck.utils.ChatUtils.format;

public class Warp implements CommandExecutor, TabCompleter {
    private EssentialServer plugin;

    public Warp(EssentialServer ins) {
        this.plugin = ins;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String noPermWarp = plugin.warpData.getString("messages.no-perm-for-warp");
        String warpNotFound = plugin.warpData.getString("messages.warp-not-found");
        List<String> warpListHeader = plugin.warpData.getStringList("messages.warp-list-header");
        List<String> warpListFooter = plugin.warpData.getStringList("messages.warp-list-footer");
        String warpListColor = plugin.warpData.getString("messages.warp-list-color");

        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length > 1) {
                ChatUtils.msg(p, "&cIncorrect usage: &e/warp &b<WarpName>");
                return true;
            } else if(args.length == 1) {
                String warpName = args[0].toLowerCase();
                if(plugin.warpFile.getAllWarps().contains(warpName)) {
                    if(p.hasPermission("warps.*") || p.hasPermission("warps." + warpName)) {
                        WarpHolder warp = plugin.warpMap.get(warpName);
                        p.playSound(warp.getLocation(), warp.getWarpSound(), 1.0F, 1.0F);
                        p.teleport(warp.getLocation());
                        ChatUtils.msg(p, warp.getWarpMessage());
                    } else {
                        ChatUtils.msg(p, noPermWarp.replaceAll("%warp%", warpName));
                    }
                } else {
                    ChatUtils.msg(p, warpNotFound.replaceAll("%warp%", warpName));
                }
            } else {
                JsonMessage warpList = new JsonMessage();
                ArrayList<String> currentWarps = new ArrayList<>(plugin.warpMap.keySet());
                Collections.sort(currentWarps);
                for(String s : currentWarps) {
                    if(p.hasPermission("warps." + s)) {
                        warpList.append(format(warpListColor + s
                                + plugin.warpData.getString("messages.warp-list-separator")))
                                .setHoverAsTooltip(format("&7Click me to warp to &e" + s))
                                .setClickAsExecuteCmd("/warp " + s)
                                .save();
                    }
                }
                // header
                for(String s : warpListHeader) p.sendMessage(format(s));
                // body - JsonMessage
                warpList.send(p);
                // footer
                for(String s : warpListFooter) p.sendMessage(format(s));
            }
        } else {
            ChatUtils.logMsg("&cYou must be a player to use this command.");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 1 && sender instanceof Player) {
            ArrayList<String> allwarps = new ArrayList<>(plugin.warpMap.keySet());
            ArrayList<String> allowedWarps = new ArrayList<>();
            for(String s : allwarps) {
                if(sender.hasPermission("warps." + s)) {
                    allowedWarps.add(s);
                }
            }
            Collections.sort(allowedWarps);
            return allowedWarps;

        } else {
            return new ArrayList<>();
        }
    }
}
