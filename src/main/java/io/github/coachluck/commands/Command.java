package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getLogger;

public class Command implements CommandExecutor {

    private final EssentialServer plugin;

    public Command(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        String pMsg = plugin.getConfig().getString("permission-message");
        String Msg = plugin.getConfig().getString("command.message");
        String Title = plugin.getConfig().getString("command.title");
        String SubT = plugin.getConfig().getString("command.sub-title");
        int fade = plugin.getConfig().getInt("command.fade");
        int stay = plugin.getConfig().getInt("command.stay");
        boolean enableMsg = plugin.getConfig().getBoolean("command.message-enable");
        boolean enableTitle = plugin.getConfig().getBoolean("command.title-enable");

        if (command.getName().equals("command")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if(player.hasPermission("essentialserver.command")) {
                    player.getPlayer();

                    return true;
                }
                else if(!(player.hasPermission("essentialserver.command"))) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("permission-message")));
                }
            } else {
                getLogger().info("You need to be a player to execute this command!");
            }
        }
        return false;
    }
}
