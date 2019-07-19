package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import static io.github.coachluck.utils.ChatUtils.*;

public class Heal implements CommandExecutor {
    private final EssentialServer plugin;
    public Heal(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String healMsg = plugin.getConfig().getString("heal.message");
        String healOtherMsg = plugin.getConfig().getString("heal.other-message");
        boolean enableMsg = plugin.getConfig().getBoolean("heal.message-enable");

        if (args.length == 0) {
            if (sender instanceof Player && sender.hasPermission("essentialserver.heal")) {
                Player player = (Player) sender;
                if (enableMsg) {
                    msg(player, format(healMsg));
                }
                player.setHealth(20);
                player.setFoodLevel(20);
            } else {
                msg(sender, format("&cYou must be a player to execute this command!"));
            }
        } else if (args.length == 1 && sender.hasPermission("essentialserver.heal.others")) {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target != null) {
                target.setHealth(20);
                target.setFoodLevel(20);
                if (enableMsg) {
                    msg(target, format(healMsg));
                    msg(sender, format(healOtherMsg.replace("%player%", target.getDisplayName())));
                }
            } else {
                msg(sender, format("&cThe specified player could not be found!"));
            }
        } else if (args.length > 1) {
            msg(sender, format("&cToo many arguments! Try /heal <player> or /heal."));
        }
        return true;
    }

}