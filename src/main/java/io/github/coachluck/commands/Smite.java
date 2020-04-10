package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import io.github.coachluck.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Smite implements CommandExecutor {
    private final EssentialServer plugin;

    public Smite(EssentialServer ins) {
        this.plugin = ins; //stores plugin
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String smiteMsg = plugin.getConfig().getString("smite.message");
        String smiteOtherMsg = plugin.getConfig().getString("smite.others-message");
        String selfMsg = plugin.getConfig().getString("smite.self-message");
        boolean enableMsg = plugin.getConfig().getBoolean("smite.message-enable");

        if (args.length == 0 && sender.hasPermission("essentialserver.smite")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Location pLoc = player.getLocation();
                player.getWorld().strikeLightning(pLoc);
                if (enableMsg) ChatUtils.msg(player, selfMsg);
            } else ChatUtils.msg(sender, "&cYou must be a player to execute this command!");

        } else if (args.length == 1 && sender.hasPermission("essentialserver.smite.others")) {
            Player target = Bukkit.getPlayerExact(args[0]);
            if(target == null) {
                ChatUtils.msg(sender, "&cThe specified player could not be found!");
                return true;
            }
            try {

                Location tLoc = target.getLocation();
                target.getWorld().strikeLightning(tLoc);
                ChatUtils.sendMessages(sender, smiteMsg, smiteOtherMsg, selfMsg, enableMsg, target);
            } catch (NullPointerException e) {
                ChatUtils.msg(sender, "&cThe specified player could not be found!");
            }
        } else if (args.length > 1) ChatUtils.msg(sender, "&cToo many arguments! Try /smite <player> or /smite.");
        return true;
    }
}