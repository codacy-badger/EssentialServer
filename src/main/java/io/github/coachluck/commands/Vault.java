package io.github.coachluck.commands;
import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import static io.github.coachluck.utils.chatUtils.format;
import static org.bukkit.Bukkit.getLogger;

public class Vault implements CommandExecutor {
    private final EssentialServer plugin;
    public Vault(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String pMsg = plugin.getConfig().getString("permission-message");
        Integer vault_size = plugin.getConfig().getInt("vault.size");
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 0 && player.hasPermission("essentialserver.vault")) {
                Inventory vault = Bukkit.createInventory(player, vault_size, ChatColor.GOLD + player.getDisplayName() + "'s Vault");
                player.openInventory(vault);
                vault.getContents();
            } else {
                player.sendMessage(format(pMsg));
            }

        } else {
            getLogger().info("You must be a player to use this command!");
        }
        return true;
    }
}