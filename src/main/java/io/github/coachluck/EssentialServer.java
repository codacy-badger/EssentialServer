package io.github.coachluck;

import io.github.coachluck.commands.*;
import io.github.coachluck.events.PlayerJoinLeave;
import io.github.coachluck.tabcompleters.PlayerTabList;
import io.github.coachluck.tabcompleters.TabList;
import io.github.coachluck.utils.Updater;
import io.github.coachluck.warps.*;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static io.github.coachluck.utils.ChatUtils.format;
import static io.github.coachluck.utils.ChatUtils.logMsg;


public class EssentialServer extends JavaPlugin {
    public boolean updateMsg = false;
    private String pMsg = format(this.getConfig().getString("permission-message"));
    public ArrayList<UUID> vanish_players = new ArrayList<>();


    public HashMap<String, WarpHolder> warpMap = new HashMap<>();
    public File warpDataFile;
    public YamlConfiguration warpData;
    public WarpFile warpFile;

    @Override
    public void onLoad() {
        enableConfig();
    }

    @Override
    public void onEnable() {
        Updater update = new Updater(this, 72032, this.getFile());
        checkUpdate(update);
        loadWarps();
        reloadWarpsMap();
        registerEvents();
        enableCommands();
        enableCommandP();
        enableCommandTabs();
    }

    @Override
    public void onDisable() {
        saveDefaultConfig();
        warpMap.clear();
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
        this.getCommand("esHelp").setExecutor(new Help(this));
        this.getCommand("es").setExecutor(new Help(this));
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
        this.getCommand("warp").setExecutor(new Warp(this));
        this.getCommand("setwarp").setExecutor(new SetWarp(this));
        this.getCommand("delwarp").setExecutor(new DelWarp(this));
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
        this.getServer().getPluginCommand("setWarp").setPermissionMessage(pMsg);
        this.getServer().getPluginCommand("warp").setPermissionMessage(pMsg);
        this.getServer().getPluginCommand("delwarp").setPermissionMessage(pMsg);
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

    private void loadWarps() {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        warpDataFile = new File(getDataFolder(), "warps.yml");
        if(!warpDataFile.exists()) {
            try {
                warpDataFile.createNewFile();
                warpData = YamlConfiguration.loadConfiguration(warpDataFile);
                warpData.options().header("This holds all information for the warps" +
                        "\n'warps' will contain all of the current warps on the server" +
                        "\n'warps.<warpname>.name - sets the display name for the warp");
                warpData.set("cooldown", 5);
                warpData.createSection("messages");
                ArrayList<String> header = new ArrayList<>();
                header.add("");
                header.add("&b&m                                  &r&7[ &c&lWarps&r &7]&b&m                                 ");
                header.add("&7Click on any warp to &eteleport there.");
                header.add("");
                warpData.set("messages.warp-list-header", header);
                warpData.set("messages.warp-list-color", "&e");
                warpData.set("messages.warp-list-separator", "&7, ");
                ArrayList<String> footer = new ArrayList<>();
                footer.add("");
                warpData.set("messages.warp-list-footer", footer);
                warpData.set("messages.warp-not-found", "&7Could not find warp&f: &c%warp%");
                warpData.set("messages.no-perm-for-warp", "&7You do not have permission to warp to &c%warp%");
                warpData.set("messages.warp-already-exists", "&c%warp% &7already exists! Try using a different name.");
                warpData.set("messages.create-warp", "You have created a new warp &e%warp%");
                warpData.createSection("warps");
                // Add the example warp
                warpData.createSection("warps.example");
                warpData.set("warps.example.on-warp-message", "&7Successfully warped to &eExample &7this is default set to the spawn location.");
                warpData.set("warps.example.on-warp-sound", Sound.BLOCK_PORTAL_TRAVEL.toString());
                warpData.createSection("warps.example.location");
                final World world = Bukkit.getWorlds().get(0);
                warpData.set("warps.example.location", world.getSpawnLocation());
                warpData.save(warpDataFile);
            } catch(IOException e) {
                e.printStackTrace();
            }
        } else {
            warpData = YamlConfiguration.loadConfiguration(warpDataFile);
        }
        warpFile = new WarpFile(this);
    }

    public void reloadWarpsMap() {
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            if(!warpMap.isEmpty()) warpMap.clear();
            for(String s : warpFile.getAllWarps()) {
                warpMap.put(s, new WarpHolder(warpFile.getLocation(s), warpFile.getSound(s), warpFile.getWarpMessage(s)));
            }
        });
    }
}