package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Spawn implements CommandExecutor {
    private final EssentialServer plugin;
    public Spawn(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {




        return true;
    }
}
