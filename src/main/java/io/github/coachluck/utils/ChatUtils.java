package io.github.coachluck.utils;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class ChatUtils {
    EssentialServer plugin;
    public ChatUtils(EssentialServer plugin) {
        this.plugin = plugin;
    }
    public static final String DARK_STAR = "★";
    public static final String WHITE_STAR = "☆";
    public static final String BIG_BLOCK = "█";
    public static final String SMALL_BLOCK = "▌";
    public static final String SMALL_DOT = "•";
    public static final String LARGE_DOT = "●";
    public static final String SMALL_ARROWS_RIGHT = "»";
    public static final String SMALL_ARROWS_LEFT = "«";
    public static final String ALERT = "⚠";
    public static final String PLUS = "✚";
    public static final String BIG_HORIZONTAL_LINE = "▍";
    public static final String SMALL_HORIZONTAL_LINE = "▏";
    public static final String CROSS = "✖";
    public static final String SLIM_CROSS = "✘";
    public static final String BOXED_CROSS = "☒";
    public static final String CHECKMARK = "✔";
    public static final String BOXED_CHECKMARK = "☑";
    public static final String LETTER = "✉";
    public static final String PICK = "⛏";
    public static final String SEPARATOR = "▬";
    public static final String TIMER = "⌛";
    public static final String LIGHTNING_BOLT = "⚡";
    public static final String SNOWFLAKE_2 = "❅";

    public static String format(String format) {
        return ChatColor.translateAlternateColorCodes('&', format);
    }

    public static void msg(CommandSender s, String message) {
        if(s instanceof Player) s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + SMALL_ARROWS_RIGHT + "&r " + message));
        else logMsg(message);
    }
    public static void logMsg(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&eEssentialServer&7]&r " + message));
    }
    //simple log formatter
    public static String logFormat(String logFormat) {
        return logFormat.replaceAll("&[a-z]", "").replaceAll("&[0-9]", "");
    }
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
}