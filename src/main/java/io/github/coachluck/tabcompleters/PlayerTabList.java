package io.github.coachluck.tabcompleters;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerTabList implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> tabs = new ArrayList<>();
        if (args.length == 1 && sender.hasPermission(cmd.getPermission() + ".others")) {
            for(Player p : Bukkit.getServer().getOnlinePlayers()) tabs.add(p.getDisplayName());
            Collections.sort(tabs);
            StringUtil.copyPartialMatches(args[0], tabs, new ArrayList<>());
            return tabs;
        }
        else return new ArrayList<>();
    }
}
