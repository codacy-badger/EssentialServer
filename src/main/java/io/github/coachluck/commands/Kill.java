package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import static io.github.coachluck.utils.ChatUtils.*;

public class Kill implements CommandExecutor {
    private final EssentialServer plugin;

    public Kill(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String killMsg = plugin.getConfig().getString("kill.message");
        String killOtherMsg = plugin.getConfig().getString("kill.others-message");
        String suicideMsg = plugin.getConfig().getString("kill.suicide-message");
        boolean enableMsg = plugin.getConfig().getBoolean("kill.message-enable");

        if (args.length == 0 && sender.hasPermission("essentialserver.kill")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (enableMsg) msg(player, suicideMsg);
                player.setHealth(0);
            } else msg(sender, "&cYou must be a player to execute this command!");
        } else if (args.length == 1 && sender.hasPermission("essentialserver.kill.others")) {
            try {
                Player target = Bukkit.getPlayerExact(args[0]);
                target.setHealth(0);
                sendMessages(sender, killMsg, killOtherMsg, suicideMsg, enableMsg, target);
            }
            catch (NullPointerException e){
                msg(sender, "&cThe specified player could not be found!");
            }
        } else if (args.length > 1) msg(sender, "&cToo many arguments! Try /kill <player> or /kill.");
        return true;
    }
}