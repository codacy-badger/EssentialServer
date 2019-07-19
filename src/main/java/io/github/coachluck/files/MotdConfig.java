package io.github.coachluck.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;


public class MotdConfig {
    private static File file;
    private static FileConfiguration motdFile;

    //Finds or generates the motd config file
    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("EssentialServer").getDataFolder(), "motd.yml");

        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                System.out.println("Couldn't create motd file!");
            }
        }
        motdFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){
        return motdFile;
    }

    public static void save(){
        try{
            motdFile.save(file);
        }catch (IOException e){
            System.out.println("Couldn't save motd file");
        }
    }

    public static void reload(){
        motdFile = YamlConfiguration.loadConfiguration(file);
    }

}


