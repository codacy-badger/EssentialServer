package io.github.coachluck.tabcompleters;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public class TabList implements TabCompleter {
    private EssentialServer plugin;

    public TabList(EssentialServer plugin) {
        this.plugin = plugin;
    }

    private static final List<String> GAME_MODES = Arrays.asList("Adventure", "Creative", "Survival");
    private static final List<String> PAGE_NUMBERS = Arrays.asList("1", "2", "3");
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> tabs = new ArrayList<>();
        if(cmd.getName().equalsIgnoreCase("gamemode")) {
            if(args.length == 1) {
                StringUtil.copyPartialMatches(args[0], GAME_MODES, new ArrayList<>());
                return GAME_MODES;
            }
            else if(args.length == 2 && sender.hasPermission("essentialserver.gamemode.others")) {
                for(Player p : Bukkit.getServer().getOnlinePlayers())
                    if(!plugin.vanish_players.contains(p.getUniqueId())) tabs.add(p.getDisplayName());
                Collections.sort(tabs);
                StringUtil.copyPartialMatches(args[1], tabs, new ArrayList<>());
                return tabs;
            }
            else return new ArrayList<>();
        }
        if(cmd.getName().equalsIgnoreCase("eshelp")) {
            if(args.length == 1) {
                StringUtil.copyPartialMatches(args[0], PAGE_NUMBERS, new ArrayList<>());
                return PAGE_NUMBERS;
            }
            else return new ArrayList<>();
        }
        if(cmd.getName().equalsIgnoreCase("teleport")) {
            if(args.length == 1) {
                for(Player p : Bukkit.getServer().getOnlinePlayers())
                    if(!plugin.vanish_players.contains(p.getUniqueId())) tabs.add(p.getDisplayName());
                Collections.sort(tabs);
                StringUtil.copyPartialMatches(args[0], tabs, new ArrayList<>());
                return tabs;
            }
            else if(args.length == 2 && sender.hasPermission("essentialserver.tp.others")) {
                for(Player p : Bukkit.getServer().getOnlinePlayers())
                    if(!plugin.vanish_players.contains(p.getUniqueId())) tabs.add(p.getDisplayName());
                Collections.sort(tabs);
                StringUtil.copyPartialMatches(args[1], tabs, new ArrayList<>());
                return tabs;
            }
            else return new ArrayList<>();
        }
        if(cmd.getName().equalsIgnoreCase("invsee")) {
            if(args.length == 1) {
                for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                    if(!plugin.vanish_players.contains(p.getUniqueId())) tabs.add(p.getDisplayName());
                }
                Collections.sort(tabs);
                StringUtil.copyPartialMatches(args[0], tabs, new ArrayList<>());
                return tabs;
            }
            else return new ArrayList<>();
        }
        return cmd.getName().equalsIgnoreCase("setspawn") ? new ArrayList<>() : new ArrayList<>();
    }
}
