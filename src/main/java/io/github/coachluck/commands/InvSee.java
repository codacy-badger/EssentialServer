package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import static io.github.coachluck.Utils.format;
import static org.bukkit.Bukkit.getLogger;

public class InvSee implements CommandExecutor {
    private final EssentialServer plugin;
    public InvSee(EssentialServer plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String openMsg = plugin.getConfig().getString("invsee.open-message");
        String closeMsg = plugin.getConfig().getString("invsee.close-message");
        boolean enableMsg = plugin.getConfig().getBoolean("kill.message-enable");

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 0 && player.hasPermission("essentialserver.invsee")) {
                Inventory inv = player.getInventory();
                player.openInventory(inv);
                if(enableMsg) {
                    player.sendMessage(format(openMsg.replace("%player%", player.getDisplayName())));
                }
            } else if (args.length == 1 && player.hasPermission("essentialserver.invsee.others")) {
                Player target = Bukkit.getPlayerExact(args[1]);
                if(target instanceof Player) {
                    Inventory target_inv = target.getInventory();
                    player.openInventory(target_inv);
                    if(enableMsg) {
                        player.sendMessage(format(openMsg));
                    }

                } else {
                    player.sendMessage(ChatColor.RED + "The specified player could not be found!");
                }
            }
        } else {
            getLogger().info("You must be a player to use this command!");
        }

        return true;
    }
}
