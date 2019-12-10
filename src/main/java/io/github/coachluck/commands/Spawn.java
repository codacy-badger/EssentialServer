package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static io.github.coachluck.utils.ChatUtils.*;


public class Spawn implements CommandExecutor {
    private final EssentialServer plugin;
    public Spawn(EssentialServer plugin) {
        this.plugin = plugin; //stores plugin
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        String spawnMsg = plugin.getConfig().getString("spawn.spawn-message");
        boolean enableMessage = plugin.getConfig().getBoolean("spawn.enable-message");

        if(cmd.getName().equalsIgnoreCase("setspawn") && sender.hasPermission("essentialserver.setspawn")) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                plugin.getConfig().set("spawn.world", p.getWorld().getName());
                plugin.getConfig().set("spawn.x", p.getLocation().getX());
                plugin.getConfig().set("spawn.y", p.getLocation().getY());
                plugin.getConfig().set("spawn.z", p.getLocation().getZ());
                plugin.getConfig().set("spawn.yaw", p.getLocation().getYaw());
                plugin.getConfig().set("spawn.pitch", p.getLocation().getPitch());
                plugin.saveConfig();
                msg(p, "&aYou have successfully set the spawn.");
            }
            else {
                logMsg("&cYou must be ingame to use this command!");
            }
        }
        else if(cmd.getName().equalsIgnoreCase("spawn") && sender.hasPermission("essentialserver.spawn")) {
            if(plugin.getConfig().get("spawn.world") != null) {
                World world = Bukkit.getWorld(plugin.getConfig().getString("spawn.world"));
                double X = plugin.getConfig().getDouble("spawn.x");
                double Y = plugin.getConfig().getDouble("spawn.y");
                double Z = plugin.getConfig().getDouble("spawn.z");
                float yaw = (float) plugin.getConfig().getDouble("spawn.yaw");
                float pitch = (float) plugin.getConfig().getDouble("spawn.pitch");
                Location spawn_loc = new Location(world, X, Y, Z, yaw, pitch);

                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    if (args.length == 0 && player.hasPermission("essentialserver.spawn")) {
                        if(enableMessage) {
                            msg(player, spawnMsg);
                        }
                        player.teleport(spawn_loc);
                    }
                    else if (args.length == 1 && player.hasPermission("essentialserver.spawn.others")) {
                        Player target = Bukkit.getPlayerExact(args[0]);
                        target.teleport(spawn_loc);
                        if(enableMessage) {
                            msg(target, spawnMsg);
                        }
                    }
                }
                else {
                    if(args.length == 0  && sender.hasPermission("essentialserver.spawn.others")) {
                        logMsg("&cInccorect usage, try &a/spawn [&bplayer&a]");
                    }
                    else if(args.length == 1 && sender.hasPermission("essentialserver.spawn.others")) {
                        Player target = Bukkit.getPlayerExact(args[0]);
                        try {
                            target.teleport(spawn_loc);
                            if (enableMessage) {
                                msg(target, spawnMsg);
                            }
                        } catch (NullPointerException e) {
                            logMsg("&cSpecified player could not be found");
                        }
                    }
                }
            }
            else {
                msg(sender, "&cPlease do &b/setspawn &cbefore you try and spawn!");
            }
        }
        return true;
    }
}
