package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import io.github.coachluck.utils.ChatUtils;
import io.github.coachluck.utils.JsonMessage;
import io.github.coachluck.warps.WarpFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommandYamlParser;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class Help implements CommandExecutor {
    private final EssentialServer plugin;
    public Help(EssentialServer plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("eshelp") && sender.hasPermission("essentialserver.eshelp")) {
            sendHelp(sender);
        return true;
        }
        else if(cmd.getName().equalsIgnoreCase("es") && sender.hasPermission("essentialserver.info")) {
            if(args.length != 1) {
                if (sender instanceof Player) {
                    JsonMessage message = new JsonMessage()
                            .append(ChatUtils.format("&8[&bEssential Server&8]&e v" + plugin.getDescription().getVersion() + " &7created by ")).save()
                            .append(ChatUtils.format("&bCoachL_ck")).setClickAsURL("https://bit.ly/37eMbW5").setHoverAsTooltip(ChatUtils.format("&eClick Me")).save()
                            .append(ChatUtils.format("&7!")).save();
                    message.send((Player) sender);
                } else ChatUtils.logMsg("&ev" + plugin.getDescription().getVersion() + " &7created by &bCoachL_ck");
            } else  {
                if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("essentialserver.reload")) {
                    plugin.reloadConfig();
                    plugin.warpData = YamlConfiguration.loadConfiguration(plugin.warpDataFile);
                    plugin.warpFile = new WarpFile(plugin);
                    ChatUtils.msg(sender, "&aSuccessfully configuration files & warps!");
                }
            }
        }
        return true;
    }

    /**
     * Gets the players specific help pages and sends them
     * @param sender : Who is running the command.
     * **/
    public void sendHelp(CommandSender sender) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            List<Command> cmdList = PluginCommandYamlParser.parse(plugin);
            p.sendMessage("");
            p.sendMessage(ChatUtils.format("&b&m                                   &r&7[ &e&lHelp&r &7]&b&m                                  "));
            JsonMessage info = new JsonMessage()
                    .append(ChatUtils.format("&7Hover over &ecommands &7for more info, &6click &7to run the command"))
                    .setHoverAsTooltip(ChatUtils.format("&7Usage with &b<> &7 is required. &c[] &7is optional")).save();
            info.send(p);
            p.sendMessage("");
            JsonMessage main = new JsonMessage();
            for (Command cmd : cmdList) {
                if (p.hasPermission(cmd.getPermission())) {
                    main = ChatUtils.getClickHelp(main, cmd);
                }
            }
            main.send(p);
            p.sendMessage("");
            p.sendMessage(ChatUtils.format("&b&m                           &r&7[ &eEssential Server&r &7]&b&m                           "));
            p.sendMessage("");
        } else {
            ChatUtils.logMsg("&cThis can only be used in game.");
        }
    }
}
