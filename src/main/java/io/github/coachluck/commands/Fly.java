package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Fly implements CommandExecutor {
    private final EssentialServer plugin;
    public Fly(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }

    private ArrayList<Player> flying_players = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String pMsg = plugin.getConfig().getString("permission-message");
        String flyMsg = plugin.getConfig().getString("fly.on-message");
        String flyOffMsg = plugin.getConfig().getString("fly.off-messaage");
        String flyOtherMsg = plugin.getConfig().getString("fly.others-on-message");
        String flyOFMsg = plugin.getConfig().getString("fly.others-off-mesage");
        boolean enableMsg = plugin.getConfig().getBoolean("fly.message-enable");

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("essentialserver.fly") && args.length == 0) {
                if(!flying_players.contains(player)) {
                    flying_players.add(player);
                    player.setAllowFlight(true);
                    if (enableMsg) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', flyMsg));
                    }
                } else if (flying_players.contains(player)) {
                    flying_players.remove(player);
                    player.setAllowFlight(false);
                    if(enableMsg) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', flyOffMsg));
                    }
                }
            } else if(args.length == 1 && player.hasPermission("essentialserver.fly.others")) {

            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', pMsg));
            }

        } else {

        }

        return false;
    }
}
