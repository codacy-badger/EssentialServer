package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static io.github.coachluck.utils.ChatUtils.*;

public class Smite implements CommandExecutor {
    private final EssentialServer plugin;
    private String smiteMsg;
    private String smiteOtherMsg;
    private String selfMsg;
    private boolean enableMsg;

    public Smite(EssentialServer ins) {
        this.plugin = ins; //stores plugin
        smiteMsg = plugin.getConfig().getString("smite.message");
        smiteOtherMsg = plugin.getConfig().getString("smite.others-message");
        selfMsg = plugin.getConfig().getString("smite.self-message");
        enableMsg = plugin.getConfig().getBoolean("smite.message-enable");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 && sender.hasPermission("essentialserver.smite")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Location pLoc = player.getLocation();
                player.getWorld().strikeLightning(pLoc);
                if (enableMsg) msg(player, selfMsg);
            } else msg(sender, "&cYou must be a player to execute this command!");

        } else if (args.length == 1 && sender.hasPermission("essentialserver.smite.others")) {
            try {
                Player target = Bukkit.getPlayerExact(args[0]);
                Location tLoc = target.getLocation();
                target.getWorld().strikeLightning(tLoc);
                sendMessages(sender, smiteMsg, smiteOtherMsg, selfMsg, enableMsg, target);
            } catch (NullPointerException e) {
                msg(sender, "&cThe specified player could not be found!");
            }
        } else if (args.length > 1) msg(sender, "&cToo many arguments! Try /smite <player> or /smite.");
        return true;
    }
}