package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import io.github.coachluck.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class InvSee implements CommandExecutor {
    private EssentialServer plugin;

    public InvSee(EssentialServer ins) {
        this.plugin = ins;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String invSeeMsg = plugin.getConfig().getString("invsee.message");
        String offlinePlayer = plugin.getConfig().getString("offline-player");
        boolean enableMsg = plugin.getConfig().getBoolean("invsee.message-enable");
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(label.equalsIgnoreCase("invsee") && sender.hasPermission("essentialserver.invsee")) { // handle invsee
                if(args.length == 0) { // open player inventory
                    ChatUtils.msg(p, "&cPlease specify a player. &a/invsee [&bplayer&a]");
                }
                else if(args.length == 1) {
                    try {
                        Player tP = Bukkit.getServer().getPlayer(args[0]);
                        PlayerInventory tInv = tP.getInventory();
                        p.openInventory(tInv);
                        if(enableMsg) ChatUtils.msg(p, invSeeMsg.replaceAll("%player%", tP.getDisplayName()));
                    } catch(NullPointerException e) {
                        ChatUtils.msg(p, offlinePlayer.replaceAll("%player%", args[0]));
                    }
                }
                else {
                    ChatUtils.msg(p, "&cIncorrect usage! Try &a/invsee [&bplayer&a]");
                }
            }
        }
        else {
            ChatUtils.msg(sender, "&cYou must be a player to use this command!");
        }
        return true;
    }
}
