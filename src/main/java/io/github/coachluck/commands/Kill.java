package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static io.github.coachluck.utils.ChatUtils.*;

public class Kill implements CommandExecutor {
    private String killMsg;
    private String killOtherMsg;
    private String suicideMsg;
    private String offlinePlayer;
    private boolean enableMsg;

    public Kill(EssentialServer ins) {
        killMsg = ins.getConfig().getString("kill.message");
        killOtherMsg = ins.getConfig().getString("kill.others-message");
        suicideMsg = ins.getConfig().getString("kill.suicide-message");
        enableMsg = ins.getConfig().getBoolean("kill.message-enable");
        offlinePlayer = ins.getConfig().getString("offline-player");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 && sender.hasPermission("essentialserver.kill")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (enableMsg) msg(player, suicideMsg);
                player.setHealth(0);
            } else msg(sender, "&cYou must be a player to use this command!");
        } else if (args.length == 1 && sender.hasPermission("essentialserver.kill.others")) {
            try {
                Player target = Bukkit.getPlayerExact(args[0]);
                target.setHealth(0);
                sendMessages(sender, killMsg, killOtherMsg, suicideMsg, enableMsg, target);
            }
            catch (NullPointerException e){
                msg(sender, offlinePlayer.replaceAll("%player%", args[0]));
            }
        } else if (args.length > 1) msg(sender, "&cToo many arguments! &7Try &e/kill &c<&bplayer&c> &7or &e/kill.");
        return true;
    }
}