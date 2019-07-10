package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import io.github.coachluck.files.MotdConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static io.github.coachluck.utils.ChatUtils.msg;
import static org.bukkit.Bukkit.getLogger;

public class ReloadCommand implements CommandExecutor {
    EssentialServer plugin;
    public ReloadCommand(EssentialServer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("essentialserver.reload")) {
                plugin.reloadConfig();
                MotdConfig.save();
                getLogger().info("Configurations have been reloaded!");
                msg(player, "&aConfiguration files have successfully reloaded!");
            }
        } else {
            MotdConfig.save();
            getLogger().info("Configurations have been reloaded!");
        }
        return true;
    }
}
