package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

import static io.github.coachluck.utils.ChatUtils.logMsg;
import static io.github.coachluck.utils.ChatUtils.msg;

public class Teleport implements CommandExecutor {
    public static HashMap<UUID, Integer> cooldownTime  = new HashMap<>();
    public static HashMap<UUID, BukkitRunnable> cooldownTask  = new HashMap<>();

    private EssentialServer plugin;
    private String tpOtherMsg;
    private String tpMsg;
    private String coolDownMsg;
    private String offlinePlayer;
    private int COOL_DOWN;
    private boolean enableMsg;

    public Teleport(EssentialServer plugin) {
        this.plugin = plugin;
        tpOtherMsg = plugin.getConfig().getString("teleport.others-message");
        tpMsg = plugin.getConfig().getString("teleport.message");
        coolDownMsg = plugin.getConfig().getString("teleport.cooldown-message");
        offlinePlayer = plugin.getConfig().getString("offline-player");
        COOL_DOWN = plugin.getConfig().getInt("teleport.cooldown-time");
        enableMsg = plugin.getConfig().getBoolean("teleport.message-enable");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && sender.hasPermission("essentialserver.tp")) {
            Player player = (Player) sender;
            UUID pUUID = player.getUniqueId();
            if(cooldownTime.containsKey(pUUID)) {
                String rem = cooldownTime.get(pUUID).toString();
                msg(player, coolDownMsg.replaceAll("%time%", rem));
            }
            else if (args.length == 0 ) {
                msg(player, "&cInsufficient arguments! &7Please try again.");
                msg(player, "&cTo teleport yourself: &e/tp &c<&botherplayer&c>");
                if (player.hasPermission("essentialserver.tp.others")) {
                    msg(player, "&cTo teleport others: &e/tp &c<&bplayer&c> <&botherplayer&c>");
                }
            }else if(args.length == 1) {
                try {
                    Player target = Bukkit.getPlayer(args[0]); //Get player from command
                    if (player.getDisplayName().equalsIgnoreCase(target.getDisplayName())) {
                        msg(player, plugin.getConfig().getString("teleport.self"));
                    } else {
                        player.teleport(target.getLocation());
                        if(enableMsg) msg(player, tpMsg.replaceAll("%player%", target.getDisplayName()));
                        if (!player.hasPermission("essentialserver.tp.bypass") && COOL_DOWN > 0) {
                            cooldownTime.put(pUUID, COOL_DOWN);
                            cooldownTask.put(pUUID, new BukkitRunnable() {
                                @Override
                                public void run() {
                                    cooldownTime.put(pUUID, cooldownTime.get(pUUID) - 1); // decrease the cooldown
                                    if (cooldownTime.get(pUUID) == 0) { // end of cooldown
                                        cooldownTime.remove(pUUID);
                                        cooldownTask.remove(pUUID);
                                        cancel();
                                    }
                                }
                            });
                            cooldownTask.get(pUUID).runTaskTimer(plugin, 20, 20);
                        }
                    }

                } catch (NullPointerException e) {
                    msg(player, offlinePlayer.replaceAll("%player%", args[0]));
                    return true;
                }
            }else if(args.length == 2 && player.hasPermission("essentialserver.tp.others")){
                try{
                Player playerToSend = Bukkit.getPlayer(args[0]);
                Player target = Bukkit.getPlayer(args[1]);
                    if(playerToSend.getDisplayName().equalsIgnoreCase(target.getDisplayName())) {
                        msg(sender, "&cDid you really mean to do that? Try again...");
                        return true;
                    }
                    else {
                        if(enableMsg) msg(sender, tpOtherMsg.replaceAll("%player1%", playerToSend.getDisplayName()).replaceAll("%player2%", target.getDisplayName()));
                        playerToSend.teleport(target.getLocation());
                        if (!player.hasPermission("essentialserver.tp.bypass") && COOL_DOWN > 0) {
                            cooldownTime.put(pUUID, COOL_DOWN);
                            cooldownTask.put(pUUID, new BukkitRunnable() {
                                @Override
                                public void run() {
                                    cooldownTime.put(pUUID, cooldownTime.get(pUUID) - 1); // decrease the cooldown
                                    if (cooldownTime.get(pUUID) == 0) { // end of cooldown
                                        cooldownTime.remove(pUUID);
                                        cooldownTask.remove(pUUID);
                                        cancel();
                                    }
                                }
                            });
                            cooldownTask.get(pUUID).runTaskTimer(plugin, 20, 20);
                        }
                    }
                }catch (NullPointerException e){
                    msg(player, offlinePlayer.replaceAll("%player%", args[1]));
                }
            }
            else if(args.length > 2) {
                if(sender.hasPermission("essentialserver.tp")) msg(sender, "&cToo many arguments! Try /tp <player>");
                if(sender.hasPermission("essentialserver.tp.others")) msg(sender, "&cToo many arguments! Try /tp <player> <player>");
            }
        }
        else logMsg("&cYou must be a player to use this command!");
        return true;
    }
}

