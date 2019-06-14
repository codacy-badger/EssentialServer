package io.github.coachluck;
import io.github.coachluck.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

public class EssentialServer extends JavaPlugin {

    @Override
    public void onEnable() {
        //Handles Configuration File
        enableConfig();

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