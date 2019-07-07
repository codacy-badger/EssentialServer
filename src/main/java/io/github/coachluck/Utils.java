package io.github.coachluck;

import org.bukkit.ChatColor;

public class Utils {
    EssentialServer plugin;

    public Utils(EssentialServer plugin) {
        this.plugin = plugin;
    }

    public static String format(String format) {
        return ChatColor.translateAlternateColorCodes('&', format);
    }

    public static String logFormat(String logFormat) {
        return logFormat.replaceAll("&[a-z]", "").replaceAll("&[0-9]", "");
    }
}