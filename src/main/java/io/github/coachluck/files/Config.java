package io.github.coachluck.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static org.bukkit.Bukkit.getLogger;

public class Config {
    private static File file;
    private static FileConfiguration configFile;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("EssentialServer").getDataFolder(), "config.yml");
        if(!(file.exists())) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {

            }
        }
        configFile = YamlConfiguration.loadConfiguration(file);

    }

    public static FileConfiguration getFile() {

        return configFile;
    }

    public static void saveFile() {
        try {
            configFile.save(file);
        }
        catch (IOException e) {
            getLogger().info("Config.yml could not be loaded! Generating a new one...");
        }

    }
    public static void reload() {
        configFile = YamlConfiguration.loadConfiguration(file);
    }
}
