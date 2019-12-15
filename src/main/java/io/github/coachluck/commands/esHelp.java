package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static io.github.coachluck.utils.ChatUtils.*;

import java.util.ArrayList;

public class esHelp implements CommandExecutor {
    private final EssentialServer plugin;
    public esHelp(EssentialServer plugin) {
        this.plugin = plugin;
    }

    private final int PAGE_LENGTH = 6;
    private int pages = 3;

    private String[] commands = {
            "&7/&6esHelp [&b#&6]&7: Get help about commands. &6[]&7 is &boptional&7.",
            "&7/&6setSpawn&7: Sets the spawn for the server.",
            "&7/&6Spawn [&bP&6]&7: Teleports you or the player to spawn.",
            "&7/&6Clear [&bP&6]&7: Clears you or the players inventory.",
            "          &b[ &6/ci&b, &6/clearinv&b, &6/clearinventory &b]",
            "&7/&6Feed [&bP&6]&7: Restores your or the players hunger.",
            "&7/&6Heal [&bP&6]&7: Heal yourself or the player specified.",
            "&7/&6God [&bP&6]&7: Makes you or the player completely invincible.",
            "&7/&6Kill [&bP&6]&7: Kill yourself or the player specified.",
            "&7/&6GameMode <&bmode&6> [&bP&6]&7: Changes your gamemode!",
            "                 &b[ &6/gm <mode> [player] &b]",
            "&7/&6Smite [&bP&6]&7: Thor is watching... &b[ &6/thor&b,&6 /lightning &b].",
            "&7/&6Burn [&bP&6]&7: Burn yourself or the specified player.",
            "&7/&6InvSee [&bP&6]&7: Let's you look inside of a players inventory.",
            "&7/&6Teleport <&bP&6> [&bP&6]&7: Teleports you or the specified players.",
            "&7/&6Fly [&bP&6]&7: Makes you or the named player take flight!",
            "&7/&6Vanish [&bP&6]&7: Makes you or the named player disappear!",
    };

    public void sendHelp(CommandSender sender, int page) {
        ArrayList<String> message = new ArrayList<>();
        message.add("");
        message.add(format(" &b&m                    &r &6&lHelp &r&7(&6&l" + (page+1) + "&r&7/&6&l" + pages + "&r&7) &b&m                    "));

        int start = page * PAGE_LENGTH;
        int end = start + PAGE_LENGTH;
        for (int i = start; i < end; i++) if (i < commands.length) message.add(format(" &b&l| &r" + commands[i]));

        sender.sendMessage(message.toArray(new String[message.size()]));
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("eshelp") && sender.hasPermission("essentialserver.esHelp")) {

            int i = 0;
            if (args.length != 0) {
                try {
                    i = Integer.parseInt(args[0]) - 1; // - 1 conversion to index
                    if (i < 0) i = 0;
                    if (i > pages-1) i = pages-1;
                } catch (NumberFormatException e) {}
            }
            sendHelp(sender, i);
        return true;
        }
        else if(cmd.getName().equalsIgnoreCase("es")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                TextComponent mainComponent = new TextComponent(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "Essential Server" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "v" + plugin.getDescription().getVersion() + ChatColor.DARK_GRAY + " by ");
                TextComponent subComponent = new TextComponent("CoachL_ck");
                subComponent.setColor(ChatColor.AQUA);
                subComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + "Author information").create()));
                subComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/essential-server.71299/"));
                mainComponent.addExtra(subComponent);
                player.spigot().sendMessage(mainComponent);
            }
            else logMsg("&ev" + plugin.getDescription().getVersion() + " &7created by &bCoachL_ck");
        }
        return true;
    }
}
