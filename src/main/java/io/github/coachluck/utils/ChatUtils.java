package io.github.coachluck.utils;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class ChatUtils {
    EssentialServer plugin;
    public ChatUtils(EssentialServer plugin) {
        this.plugin = plugin;
    }
    public static final String SMALL_ARROWS_RIGHT = "»";

    /**
     * Simple string formatter
     * @param format the string to translate color codes
     * **/
    public static String format(String format) {
        return ChatColor.translateAlternateColorCodes('&', format);
    }

    /**
     * Translates color codes and chooses how to message the sender, includes prefix
     * @param s determines if it is a player message or a console message
     * @param message the message to send
     * **/
    public static void msg(CommandSender s, String message) {
        if(s instanceof Player) s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + SMALL_ARROWS_RIGHT + "&r " + message));
        else logMsg(message);
    }
    /**
     * Logs the message to console with plugin prefix
     * @param message the message to color code and send to console
     * **/
    public static void logMsg(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&eEssentialServer&7]&r " + message));
    }
    /**
     * Basic Message handler
     * @param sender who to send the messages too
     * @param mainMsg the main command usage message
     * @param otherMsg the message for usage on other players
     * @param selfMsg the message to be used when performed on themselves
     * @param enableMsg if the messages should even send
     * @param target who the action is being done too
     * **/
    public static void sendMessages(CommandSender sender, String mainMsg, String otherMsg, String selfMsg, boolean enableMsg, Player target) {
        if (enableMsg) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                if (!p.getDisplayName().equalsIgnoreCase(target.getDisplayName())) {
                    msg(target, mainMsg);
                    msg(p, otherMsg.replace("%player%", target.getDisplayName()));
                } else msg(p, selfMsg);
            } else if (sender instanceof ConsoleCommandSender) {
                msg(target, mainMsg);
                msg(sender, otherMsg.replace("%player%", target.getDisplayName()));
            }
        }
    }
    /**
     * Makes the help page main body from a JsonMessage, and a player, used within a loop of commands
     * @param jMsg the JsonMessage to update
     * @param cmd the command to extract name, usage, description from
     * **/
    public static JsonMessage getClickHelp(JsonMessage jMsg, Command cmd) {
        String cmdName = cmd.getName();
        jMsg.append(format("&e" + cmd.getName() + "&7, ")).setHoverAsTooltip(format("&b" + cmdName + "&7: &e" + cmd.getUsage()), format("&7" + cmd.getDescription())).setClickAsSuggestCmd("/" + cmdName.toLowerCase() + " ").save();
        return jMsg;
    }
}