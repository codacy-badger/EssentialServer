package io.github.coachluck;

import io.github.coachluck.commands.*;
import io.github.coachluck.events.PlayerJoinLeave;
import io.github.coachluck.tabcompleters.PlayerTabList;
import io.github.coachluck.tabcompleters.TabList;
import io.github.coachluck.utils.Updater;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

import static io.github.coachluck.utils.ChatUtils.*;

@SuppressWarnings("unused")
public class EssentialServer extends JavaPlugin {
    public boolean updateMsg = false;
    private String pMsg = this.getConfig().getString("permission-message");
    private boolean updateType = this.getConfig().getBoolean("enable-auto-update");
    private Updater.UpdateType UPDATE_CHOICE = Updater.UpdateType.DEFAULT;

    @Override
    public void onEnable() {
        if(!updateType)  UPDATE_CHOICE = Updater.UpdateType.NO_DOWNLOAD;
        Logger logger = this.getLogger();
        Updater update = new Updater(this, 72032, this.getFile(), UPDATE_CHOICE, true);
        if(update.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE && !updateType) {
            updateMsg = true;
            logMsg("&bThere is a new update available! &ehttps://bit.ly/37eMbW5");
        } else {
            updateMsg = false;
            logMsg("&bYou can disable auto-updates in the &econfig.yml");
        }
        enableConfig();
        registerEvents();
        enableCommands();
        enableCommandP();
        enableCommandTabs();
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
        this.getCommand("InvSee").setExecutor(new InvSee(this));
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
        this.getServer().getPluginCommand("InvSee").setPermissionMessage(format(pMsg));
    }

    private void enableCommandTabs() {
        this.getCommand("gameMode").setTabCompleter(new TabList());
        this.getCommand("esHelp").setTabCompleter(new TabList());
        this.getCommand("InvSee").setTabCompleter(new TabList());
        this.getCommand("Teleport").setTabCompleter(new TabList());
        this.getCommand("SetSpawn").setTabCompleter(new TabList());
        this.getCommand("Clear").setTabCompleter(new PlayerTabList());
        this.getCommand("Spawn").setTabCompleter(new PlayerTabList());
        this.getCommand("God").setTabCompleter(new PlayerTabList());
        this.getCommand("Fly").setTabCompleter(new PlayerTabList());
        this.getCommand("Heal").setTabCompleter(new PlayerTabList());
        this.getCommand("Feed").setTabCompleter(new PlayerTabList());
        this.getCommand("Vanish").setTabCompleter(new PlayerTabList());
        this.getCommand("Burn").setTabCompleter(new PlayerTabList());
        this.getCommand("Smite").setTabCompleter(new PlayerTabList());
        this.getCommand("Kill").setTabCompleter(new PlayerTabList());
    }
}