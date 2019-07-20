package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import io.github.coachluck.files.MotdConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


import static io.github.coachluck.utils.ChatUtils.format;
import static io.github.coachluck.utils.ChatUtils.msg;

public class ReloadCommand implements CommandExecutor {
    EssentialServer plugin;
    public ReloadCommand(EssentialServer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        //add support for motd file
        MotdConfig.reload();
        plugin.reloadConfig();
        msg(sender, format("&aConfiguration files have been reloaded!"));
        return true;
    }
}
