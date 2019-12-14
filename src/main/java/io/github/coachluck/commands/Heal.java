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
        int amt = plugin.getConfig().getInt("heal.amount");
        String healOtherMsg = plugin.getConfig().getString("heal.other-message");
        boolean enableMsg = plugin.getConfig().getBoolean("heal.message-enable");

        if (args.length == 0 && sender.hasPermission("essentialserver.heal")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                double h = player.getHealth();
                int f = player.getFoodLevel();
                double hAmt = h + amt;
                if(hAmt > 20.0) hAmt = 20.0;
                int fAmt = f + amt;
                if(fAmt > 20) fAmt = 20;
                player.setHealth(hAmt);
                player.setFoodLevel(fAmt);
                player.setFireTicks(0);
                if (enableMsg) msg(player, healMsg);
            } else msg(sender, format("&cYou must be a player to execute this command!"));
        } else if (args.length == 1 && sender.hasPermission("essentialserver.heal.others")) {
            try {
                Player target = Bukkit.getPlayerExact(args[0]);
                double h = target.getHealth();
                int f = target.getFoodLevel();
                double hAmt = h + amt;
                if(hAmt > 20.0) hAmt = 20.0;
                int fAmt = f + amt;
                if(fAmt > 20) fAmt = 20;
                target.setHealth(hAmt);
                target.setFoodLevel(fAmt);
                target.setFireTicks(0);
                if (enableMsg) {
                    if(sender instanceof Player) {
                        Player p = (Player) sender;
                        if (!p.getDisplayName().equalsIgnoreCase(target.getDisplayName())) {
                            msg(target, healMsg);
                            msg(p, healOtherMsg.replace("%player%", target.getDisplayName()));
                        } else msg(p, healMsg);
                    } else if (sender instanceof ConsoleCommandSender) {
                        msg(target, healMsg);
                        msg(sender, healOtherMsg.replace("%player%", target.getDisplayName()));
                    }
                }
            } catch (NullPointerException e) {
                msg(sender, "&cThe specified player could not be found!");
            }
        } else if (args.length > 1) msg(sender, "&cToo many arguments! Try /heal <player> or /heal.");
        return true;
    }

}