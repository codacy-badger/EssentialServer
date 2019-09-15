package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
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

        if (args.length == 0 && sender.hasPermission("essentialserver.heal")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (enableMsg) {
                    msg(player, format(healMsg));
                }
                player.setHealth(20);
                player.setFoodLevel(20);
                player.setFireTicks(0);
            } else {
                msg(sender, format("&cYou must be a player to execute this command!"));
            }
        } else if (args.length == 1 && sender.hasPermission("essentialserver.heal.others")) {
            Player target = Bukkit.getPlayerExact(args[0]);
            try {
                target.setHealth(20);
                target.setFoodLevel(20);
                target.setFireTicks(0);
                if (enableMsg) {
                    if(sender instanceof Player) {
                        Player p = (Player) sender;
                        if (!p.getDisplayName().equalsIgnoreCase(target.getDisplayName())) {
                            msg(target, format(healMsg));
                            msg(p, format(healOtherMsg.replace("%player%", target.getDisplayName())));
                        } else {
                            msg(p, format(healMsg));
                        }
                    } else if (sender instanceof ConsoleCommandSender) {
                        msg(target, format(healMsg));
                        msg(sender, format(healOtherMsg.replace("%player%", target.getDisplayName())));
                    }
                }
            } catch (NullPointerException e) {
                msg(sender, format("&cThe specified player could not be found!"));
            }
        } else if (args.length > 1) {
            msg(sender, format("&cToo many arguments! Try /heal <player> or /heal."));
        }
        return true;
    }

}