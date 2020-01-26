package io.github.coachluck.tabcompleters;

import io.github.coachluck.EssentialServer;
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
    private EssentialServer plugin;

    public PlayerTabList(EssentialServer plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> tabs = new ArrayList<>();
        if (args.length == 1 && sender.hasPermission(cmd.getPermission() + ".others")) {
            for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                if(!cmd.getName().equalsIgnoreCase("vanish")) {
                    if(!plugin.vanish_players.contains(p.getUniqueId())) tabs.add(p.getDisplayName());
                } else {
                    tabs.add(p.getDisplayName());
                }
            }
            Collections.sort(tabs);
            StringUtil.copyPartialMatches(args[0], tabs, new ArrayList<>());
            return tabs;
        }
        else return new ArrayList<>();
    }
}
