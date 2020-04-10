package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import io.github.coachluck.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Heal implements CommandExecutor {
    private EssentialServer plugin;
    private int healAmount;

    public Heal(EssentialServer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String healMsg = plugin.getConfig().getString("heal.message");
        healAmount = plugin.getConfig().getInt("heal.amount");
        String healOtherMsg = plugin.getConfig().getString("heal.other-message");
        boolean enableMsg = plugin.getConfig().getBoolean("heal.message-enable");

        if (args.length == 0 && sender.hasPermission("essentialserver.heal")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                setHealth(player);
                if (enableMsg) ChatUtils.msg(player, healMsg);
            } else
                ChatUtils.msg(sender, "&cYou must be a player to execute this command!");
        } else if (args.length == 1 && sender.hasPermission("essentialserver.heal.others")) {
            Player target = Bukkit.getPlayerExact(args[0]);
            if(target == null) {
                ChatUtils.msg(sender, "&cThe specified player could not be found!");
                return true;
            }
            setHealth(target);
            ChatUtils.sendMessages(sender, healMsg, healOtherMsg, healMsg, enableMsg, target);
        } else if (args.length > 1) ChatUtils.msg(sender, "&cToo many arguments! Try /heal <player> or /heal.");
        return true;
    }

    private void setHealth(Player p) {
        double h = p.getHealth();
        int f = p.getFoodLevel();
        double hAmt = h + healAmount;
        double maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        if(hAmt > maxHealth) hAmt = maxHealth;
        int fAmt = f + healAmount;
        if(fAmt > 20) fAmt = 20;
        p.setHealth(hAmt);
        p.setFoodLevel(fAmt);
        p.setFireTicks(0);
    }

}