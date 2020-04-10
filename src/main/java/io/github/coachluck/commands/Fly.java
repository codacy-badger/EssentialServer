package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import io.github.coachluck.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Fly implements CommandExecutor {
    private final EssentialServer plugin;
    public Fly(EssentialServer plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String flyOtherOnMsg = plugin.getConfig().getString("fly.other-on-message");
        String flyOtherOffMsg = plugin.getConfig().getString("fly.other-off-message");
        boolean enableMsg = plugin.getConfig().getBoolean("fly.message-enable");

        if (args.length == 0 && sender.hasPermission("essentialserver.fly")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                flightCheck(p);
            }
            else ChatUtils.msg(sender, "&cYou must be a player to execute this command!");
        }
        else if (args.length == 1 && sender.hasPermission("essentialserver.fly.others")) {
            Player tP = Bukkit.getPlayerExact(args[0]);
            if(tP == null) {
                ChatUtils.msg(sender,"&cThe specified player could not be found");
                return true;
            }
            flightCheck(tP);
            if (enableMsg && !tP.getName().equals(sender.getName())) {
                if (tP.getAllowFlight()) ChatUtils.msg(sender, flyOtherOnMsg.replace("%player%", tP.getDisplayName()));
                else ChatUtils.msg(sender, flyOtherOffMsg.replace("%player%", tP.getDisplayName()));
            }
        }
        else if (args.length > 1) ChatUtils.msg(sender, "&cToo many arguments! Try /fly <player> or /fly.");
        return true;
    }

    private void flightCheck(Player player) {
        String flyMsg = plugin.getConfig().getString("fly.on-message");
        String flyOffMsg = plugin.getConfig().getString("fly.off-message");
        boolean enableMsg = plugin.getConfig().getBoolean("fly.message-enable");
        if (player.getAllowFlight()) {
            player.setAllowFlight(false);
            if (enableMsg)
                ChatUtils.msg(player, flyOffMsg.replace("%player%", player.getDisplayName()));
        }
        else {
            player.setAllowFlight(true);
            if (enableMsg)
                ChatUtils.msg(player, flyMsg.replace("%player%", player.getDisplayName()));
        }
    }
}