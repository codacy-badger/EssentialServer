package io.github.coachluck;
import io.github.coachluck.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class EssentialServer extends JavaPlugin {

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this);
        metrics.addCustomChart(new Metrics.MultiLineChart("players_and_servers", new Callable<Map<String, Integer>>() {
            @Override
            public Map<String, Integer> call() throws Exception {
                Map<String, Integer> valueMap = new HashMap<>();
                valueMap.put("servers", 1);
                valueMap.put("players", Bukkit.getOnlinePlayers().size());
                return valueMap;
            }
        }));

        //Handles Configuration File
        enableConfig();
        this.getServer().getPluginCommand("Clear").setPermissionMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("permission-message") ));
        getLogger().info("Plugin enabling...");
        //Enables Event Listeners

        getLogger().info("Event Listeners enabled successfully!");
        //Enables Command Classes
        this.getCommand("Command").setExecutor(new Command(this));
        this.getCommand("esHelp").setExecutor(new esHelp(this));
        this.getCommand("Feed").setExecutor(new Feed(this));
        this.getCommand("Heal").setExecutor(new Heal(this));
        this.getCommand("God").setExecutor((new God(this)));
        this.getCommand("Kill").setExecutor(new Kill(this));
        this.getCommand("Clear").setExecutor(new Clear(this));
        getLogger().info("Commands enabled successfully!");

        getLogger().info("Plugin enabled successfully!");
    }
    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled");
        saveDefaultConfig();
    }
private void enableConfig() {
    getConfig().options().copyDefaults(true);
    saveDefaultConfig();
}
}