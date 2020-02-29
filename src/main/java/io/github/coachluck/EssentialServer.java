package io.github.coachluck;

import io.github.coachluck.commands.*;
import io.github.coachluck.events.PlayerJoinLeave;
import io.github.coachluck.tabcompleters.PlayerTabList;
import io.github.coachluck.tabcompleters.TabList;
import io.github.coachluck.utils.Updater;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

import static io.github.coachluck.utils.ChatUtils.format;
import static io.github.coachluck.utils.ChatUtils.logMsg;


@SuppressWarnings("unused")
public class EssentialServer extends JavaPlugin {
    public boolean updateMsg = false;
    private String pMsg = format(this.getConfig().getString("permission-message"));
    public ArrayList<UUID> vanish_players = new ArrayList<>();

    @Override
    public void onEnable() {
        enableConfig();
        Logger logger = this.getLogger();
        Updater update = new Updater(this, 72032, this.getFile(), false);
        checkUpdate(update);
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
        this.getServer().getPluginCommand("es").setPermissionMessage(pMsg);
        this.getServer().getPluginCommand("esHelp").setPermissionMessage(pMsg);
        this.getServer().getPluginCommand("Clear").setPermissionMessage(pMsg);
        this.getServer().getPluginCommand("Smite").setPermissionMessage(pMsg);
        this.getServer().getPluginCommand("Fly").setPermissionMessage(pMsg);
        this.getServer().getPluginCommand("God").setPermissionMessage(pMsg);
        this.getServer().getPluginCommand("Kill").setPermissionMessage(pMsg);
        this.getServer().getPluginCommand("Heal").setPermissionMessage(pMsg);
        this.getServer().getPluginCommand("Feed").setPermissionMessage(pMsg);
        this.getServer().getPluginCommand("gameMode").setPermissionMessage(pMsg);
        this.getServer().getPluginCommand("Teleport").setPermissionMessage(pMsg);
        this.getServer().getPluginCommand("Vanish").setPermissionMessage(pMsg);
        this.getServer().getPluginCommand("Burn").setPermissionMessage(pMsg);
        this.getServer().getPluginCommand("Spawn").setPermissionMessage(pMsg);
        this.getServer().getPluginCommand("SetSpawn").setPermissionMessage(pMsg);
        this.getServer().getPluginCommand("InvSee").setPermissionMessage(pMsg);
    }

    private void enableCommandTabs() {
        this.getCommand("gameMode").setTabCompleter(new TabList(this));
        this.getCommand("esHelp").setTabCompleter(new TabList(this));
        this.getCommand("InvSee").setTabCompleter(new TabList(this));
        this.getCommand("Teleport").setTabCompleter(new TabList(this));
        this.getCommand("SetSpawn").setTabCompleter(new TabList(this));
        this.getCommand("Clear").setTabCompleter(new PlayerTabList(this));
        this.getCommand("Spawn").setTabCompleter(new PlayerTabList(this));
        this.getCommand("God").setTabCompleter(new PlayerTabList(this));
        this.getCommand("Fly").setTabCompleter(new PlayerTabList(this));
        this.getCommand("Heal").setTabCompleter(new PlayerTabList(this));
        this.getCommand("Feed").setTabCompleter(new PlayerTabList(this));
        this.getCommand("Vanish").setTabCompleter(new PlayerTabList(this));
        this.getCommand("Burn").setTabCompleter(new PlayerTabList(this));
        this.getCommand("Smite").setTabCompleter(new PlayerTabList(this));
        this.getCommand("Kill").setTabCompleter(new PlayerTabList(this));
    }

    private void checkUpdate(Updater update) {
        Updater.UpdateResult up = update.getResult();
        if (Updater.UpdateResult.DISABLED != up) {
            if (up == Updater.UpdateResult.UPDATE_AVAILABLE) {
                updateMsg = true;
                logMsg("&bThere is a new update available! &ehttps://bit.ly/37eMbW5");
                logMsg("&bYou can enable automatic updates in the &e'auto-update.yml&b' by changing '&eforce-download&b' to true");
            }
            else if (up == Updater.UpdateResult.SUCCESS) {
                logMsg("&bSuccessfully installed the newest version:&e " + update.getLatestName());
                logMsg("&bPlease reload the server for this to take effect.");
            }
            else if (up == Updater.UpdateResult.NO_UPDATE) {
                logMsg("&bYou are running the latest version.");
            }
        }
    }
}