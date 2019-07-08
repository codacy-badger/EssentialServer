package io.github.coachluck.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressWarnings("unused")
public class Debugger {

    private static final String IN_GAME_DEBUG = ChatColor.GREEN + ChatColor.BOLD.toString() + "[SPU-G-DEBUG] ";
    private static final String CONSOLE_DEBUG = "[SPU-C-DEBUG] ";
    private static final String EXCEPTION_CAUGHT_PREFIX = "[SPU-EXCEPTION-DEBUG] ";

    public static void sendInGameDebug(Player player, String igMessage){
        player.sendMessage(IN_GAME_DEBUG + igMessage);
    }
    public static void sendConsoleMessage(String cMessage){
        Bukkit.getLogger().info(CONSOLE_DEBUG + cMessage);
    }
    public static void sendExceptionDebug(String eMessage){
        System.out.println(EXCEPTION_CAUGHT_PREFIX + eMessage);
    }

    public static void writeDebugFile(String content, String location){
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;

        DateFormat format = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
        Calendar calendar = Calendar.getInstance();

        try {
            String fileMessage = content;

            fileWriter = new FileWriter(location + format.format(calendar));
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(fileMessage);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if(fileWriter != null){
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}