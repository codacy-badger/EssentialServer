package io.github.coachluck.utils;

import io.github.coachluck.EssentialServer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@SuppressWarnings("unused")
public class ChatUtils {
    EssentialServer plugin;
    public ChatUtils(EssentialServer plugin) {
        this.plugin = plugin;
    }
    public static final String DARK_STAR = "★";
    public static final String WHITE_STAR = "☆";
    public static final String CIRCLE_BLANK_STAR = "✪";
    public static final String BIG_BLOCK = "█";
    public static final String SMALL_BLOCK = "▌";
    public static final String SMALL_DOT = "•";
    public static final String LARGE_DOT = "●";
    public static final String HEART = "♥";
    public static final String SMALL_ARROWS_RIGHT = "»";
    public static final String SMALL_ARROWS_LEFT = "«";
    public static final String ALERT = "⚠";
    public static final String RADIOACTIVE = "☢";
    public static final String BIOHAZARD = "☣";
    public static final String PLUS = "✚";
    public static final String BIG_HORIZONTAL_LINE = "▍";
    public static final String SMALL_HORIZONTAL_LINE = "▏";
    public static final String PLAY = "▶";
    public static final String GOLD_ICON = "❂";
    public static final String CROSS = "✖";
    public static final String SLIM_CROSS = "✘";
    public static final String BOXED_CROSS = "☒";
    public static final String CHECKMARK = "✔";
    public static final String BOXED_CHECKMARK = "☑";
    public static final String LETTER = "✉";
    public static final String BLACK_CHESS_KING = "♚";
    public static final String BLACK_CHESS_QUEEN = "♛";
    public static final String SKULL_AND_CROSSBONES = "☠";
    public static final String WHITE_FROWNING_FACE = "☹";
    public static final String WHITE_SMILING_FACE = "☺";
    public static final String BLACK_SMILING_FACE = "☻";
    public static final String PICK = "⛏";
    public static final String SEPARATOR = "▬";
    public static final String AEROPLANE = "✈";
    public static final String TIMER = "⌛";
    public static final String LIGHTNING_BOLT = "⚡";
    public static final String SNOWFLAKE_1 = "❆";
    public static final String SNOWFLAKE_2 = "❅";
    public static final String NUKE = "☢";

    //simple chat formatter
    public static String format(String format) {
        return ChatColor.translateAlternateColorCodes('&', format);
    }

    public void msg(CommandSender s, String message) {
        s.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    //simple log formatter
    public static String logFormat(String logFormat) {
        return logFormat.replaceAll("&[a-z]", "").replaceAll("&[0-9]", "");
    }
}