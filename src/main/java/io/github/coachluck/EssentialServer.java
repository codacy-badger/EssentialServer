package io.github.coachluck;

import io.github.coachluck.commands.*;
import io.github.coachluck.events.PlayerJoinLeave;
import io.github.coachluck.files.MotdConfig;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

import static io.github.coachluck.utils.ChatUtils.*;

@SuppressWarnings("unused")
public class EssentialServer extends JavaPlugin {
    public ArrayList<Player> vanish_players = new ArrayList<>();

    String pMsg = this.getConfig().getString("permission-message");
    @Override
    public void onEnable() {
        //Handles Configuration File
        enableConfig();

        //Enables Event Listeners
        registerEvents();

        //Enables Command Classes
        enableCommands();
        enableCommandP();

        getLogger().info("Plugin enabled successfully!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled");
        saveDefaultConfig();
        MotdConfig.save();
    }

    public void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerJoinLeave(this), this);
        getLogger().info("Event Listeners enabled successfully!");
    }
    private void enableConfig() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        MotdConfig.setup();
        MotdConfig.get().addDefault("join-message", "&6%player% &bhas joined the server!");
        MotdConfig.get().addDefault("leave-message", "&6%player% &bhas left the server!");
        MotdConfig.get().options().copyDefaults(true);
        MotdConfig.save();
    }
    private void enableCommands() {
        this.getCommand("esReload").setExecutor(new ReloadCommand(this));
        this.getCommand("InvSee").setExecutor(new InvSee(this));
        this.getCommand("Fly").setExecutor(new Fly(this));
        this.getCommand("esHelp").setExecutor(new esHelp(this));
        this.getCommand("Feed").setExecutor(new Feed(this));
        this.getCommand("Heal").setExecutor(new Heal(this));
        this.getCommand("God").setExecutor((new God(this)));
        this.getCommand("Kill").setExecutor(new Kill(this));
        this.getCommand("Clear").setExecutor(new Clear(this));
      //  this.getCommand("Teleport").setExecutor(new Teleport(this));
        this.getCommand("Vanish").setExecutor(new Vanish(this));
        getLogger().info("Commands enabled successfully!");
    }
    private void enableCommandP() {
        this.getServer().getPluginCommand("Clear").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("esReload").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("Fly").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("God").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("Kill").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("Heal").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("Feed").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("esHelp").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("InvSee").setPermissionMessage(format(pMsg));
        this.getServer().getPluginCommand("Vanish").setPermissionMessage(format(pMsg));

    }
}