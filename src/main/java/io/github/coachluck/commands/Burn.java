package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static io.github.coachluck.utils.ChatUtils.*;

public class Burn implements CommandExecutor {
    private final EssentialServer plugin;

    public Burn(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String burnMsg = plugin.getConfig().getString("burn.message");
        String burnOtherMsg = plugin.getConfig().getString("burn.others-message");
        String selfMsg = plugin.getConfig().getString("burn.self-message");
        boolean enableMsg = plugin.getConfig().getBoolean("burn.message-enable");
        int BURN_SECONDS = plugin.getConfig().getInt("burn.burn-time");
        double BURN_TICKS = 20;
        int BURN = BURN_SECONDS * (int) BURN_TICKS;

        if (args.length == 0) {
            if (sender instanceof Player && sender.hasPermission("essentialserver.burn")) {
                Player player = (Player) sender;
                if (enableMsg) {
                    msg(player, format(selfMsg));
                }
                player.setFireTicks(BURN);
            } else {
                msg(sender, format("&cYou must be a player to execute this command!"));
            }
        } else if (args.length == 1 && sender.hasPermission("essentialserver.burn.others")) {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target != null) {
                target.setFireTicks(BURN);
                if (enableMsg) {
                    msg(target, format(burnMsg));
                    msg(sender, format(burnOtherMsg.replace("%player%", target.getDisplayName())));
                }
            } else {
                msg(sender, format("&cThe specified player could not be found!"));
            }
        } else if (args.length > 1) {
            msg(sender, format("&cToo many arguments! Try /burn <player> or /burn."));
        }
        return true;
    }
}