package io.github.coachluck.utils;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("unused")
public class BossBar {

    public static org.bukkit.boss.BossBar bossBar;

    public BossBar(String message, String color, String style, double progress){

        bossBar = Bukkit.createBossBar(message, BarColor.valueOf(color), BarStyle.valueOf(style), BarFlag.DARKEN_SKY);
        bossBar.setProgress(progress);
    }

    public static void hide(Player player){
        bossBar.setVisible(false);
        bossBar.removeAll();
    }

    public static void send(Player player, int delay){
        bossBar.addPlayer(player);
        bossBar.setVisible(true);
        Bukkit.getScheduler().runTaskLater((Plugin) Bukkit.getServer(), new Runnable() {
            @Override
            public void run() {
                hide(player);
            }
        }, delay);
    }
}