package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import io.github.coachluck.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class God implements CommandExecutor {
    private final EssentialServer plugin;
    public God(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String godOtherMsg = plugin.getConfig().getString("god.others-on-message");
        String godOtherOffMsg = plugin.getConfig().getString("god.others-off-message");
        boolean enableMsg = plugin.getConfig().getBoolean("god.message-enable");

        if(args.length == 0 && sender.hasPermission("essentialserver.god")) {
            if(sender instanceof Player) godCheck((Player) sender);
            else ChatUtils.msg(sender, ChatUtils.format("&cYou must be a player to execute this command!"));
        }
        else if (args.length == 1 && sender.hasPermission("essentialserver.god.others")) {
            try {
            Player target = Bukkit.getPlayerExact(args[0]);
                godCheck(target);
                if(enableMsg && !target.getName().equals(sender.getName())) {
                    if (target.isInvulnerable()) ChatUtils.msg(sender, godOtherMsg.replace("%player%", target.getDisplayName()));
                    else if (!target.isInvulnerable()) ChatUtils.msg(sender, godOtherOffMsg.replace("%player%", target.getDisplayName()));
                }
            } catch (NullPointerException e) { ChatUtils.msg(sender, "&cThe specified player could not be found"); }
        }
        else if (args.length > 1) ChatUtils.msg(sender, "&cToo many arguments! Try /god <player> or /god.");
        return true;
    }

    private void godCheck(Player player) {
        String godMsg = plugin.getConfig().getString("god.on-message");
        String godOffMsg = plugin.getConfig().getString("god.off-message");
        boolean enableMsg = plugin.getConfig().getBoolean("god.message-enable");

        if(player.isInvulnerable()) {
            player.setInvulnerable(false);
            if (enableMsg) ChatUtils.msg(player, godOffMsg.replace("%player%", player.getDisplayName()));
        } else {
            player.setInvulnerable(true);
            if (enableMsg) ChatUtils.msg(player, godMsg.replace("%player%", player.getDisplayName()));
        }
    }
}
