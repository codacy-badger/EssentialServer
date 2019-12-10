package io.github.coachluck;

import io.github.coachluck.commands.*;
import io.github.coachluck.events.PlayerJoinLeave;
import io.github.coachluck.utils.UpdateChecker;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

import static io.github.coachluck.utils.ChatUtils.*;

@SuppressWarnings("unused")
public class EssentialServer extends JavaPlugin {
    public ArrayList<UUID> vanish_players = new ArrayList<>();
    public boolean updateMsg = false;
    private String pMsg = this.getConfig().getString("permission-message");
    @Override
    public void onEnable() {
        //Handles Configuration File
        Logger logger = this.getLogger();

        new UpdateChecker(this, 71299).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logMsg("&rRunning the latest version -&b " + this.getDescription().getVersion());
                updateMsg = false;
            } else {
                logMsg("&cThere is a new update available! Go to the spigot page:&e http://bit.ly/346mO6j");

                updateMsg = true;
            }
        });
        enableConfig();

        //Enables Event Listeners
        registerEvents();

        //Enables Command Classes
        enableCommands();
        enableCommandP();
    }

    @Override
    public void onDisable() {
        saveDefaultConfig();
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerJoinLeave(this), this);
    }
    private void enableConfig() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }
    private void enableCommands() {
        this.getCommand("esHelp").setExecutor(new esHelp(this));
        this.getCommand("es").setExecutor(new esHelp(this));
        this.getCommand("Spawn").setExecutor(new Spawn(this));
        this.getCommand("Smite").setExecutor(new Smite(this));
        this.getCommand("Fly").setExecutor(new Fly(this));
        this.getCommand("Feed").setExecutor(new Feed(this));
        this.getCommand("Heal").setExecutor(new Heal(this));
        this.getCommand("God").setExecutor((new God(this)));
        this.getCommand("Kill").setExecutor(new Kill(this));
        this.getCommand("Clear").setExecutor(new Clear(this));
        this.getCommand("Burn").setExecutor(new Burn(this));
        this.getCommand("gameMode").setExecutor(new gameMode(this));
        this.getCommand("Teleport").setExecutor(new Teleport(this));
        this.getCommand("Vanish").setExecutor(new Vanish(this));
        this.getCommand("SetSpawn").setExecutor(new Spawn(this));
    }
    private void enableCommandP() {
        this.getServer().getPluginCommand("esHelp").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("Clear").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("Smite").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("Fly").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("God").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("Kill").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("Heal").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("Feed").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("gameMode").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("Teleport").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("Vanish").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("Burn").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("Spawn").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("SetSpawn").setPermissionMessage(format(pMsg));
    }
}