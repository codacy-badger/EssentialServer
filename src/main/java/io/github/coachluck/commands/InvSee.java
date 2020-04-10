package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import static io.github.coachluck.utils.ChatUtils.msg;

public class InvSee implements CommandExecutor {

    private String invSeeMsg;
    private String offlinePlayer;
    private boolean enableMsg;

    public InvSee(EssentialServer ins) {
        invSeeMsg = ins.getConfig().getString("invsee.message");
        offlinePlayer = ins.getConfig().getString("offline-player");
        enableMsg = ins.getConfig().getBoolean("invsee.message-enable");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(label.equalsIgnoreCase("invsee") && sender.hasPermission("essentialserver.invsee")) { // handle invsee
                if(args.length == 0) { // open player inventory
                    msg(p, "&cPlease specify a player. &a/invsee [&bplayer&a]");
                }
                else if(args.length == 1) {
                    try {
                        Player tP = Bukkit.getServer().getPlayer(args[0]);
                        PlayerInventory tInv = tP.getInventory();
                        p.openInventory(tInv);
                        if(enableMsg) msg(p, invSeeMsg.replaceAll("%player%", tP.getDisplayName()));
                    } catch(NullPointerException e) {
                        msg(p, offlinePlayer.replaceAll("%player%", args[0]));
                    }
                }
                else {
                    msg(p, "&cIncorrect usage! Try &a/invsee [&bplayer&a]");
                }
            }
        }
        else {
            msg(sender, "&cYou must be a player to use this command!");
        }
        return true;
    }
}
