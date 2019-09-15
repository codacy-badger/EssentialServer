package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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

            "&b/&cesHelp [&b#&c]&7: Get help about commands. &c[]&7 is &boptional&7.",
            "&b/&csetSpawn&7: Sets the spawn for the server.",
            "&b/&cSpawn [&bP&c]&7: Teleports you or the player to spawn.",
            "&b/&cClear [&bP&c]&7: Clears you or the players inventory.",
            "          &b[ &c/ci&b, &c/clearinv&b, &c/clearinventory &b]",
            "&b/&cFeed [&bP&c]&7: Restores your or the players hunger.",
            "&b/&cHeal [&bP&c]&7: Heal yourself or the player specified.",
            "&b/&cGod [&bP&c]&7: Makes you or the player completely invincible.",
            "&b/&cKill [&bP&c]&7: Kill yourself or the player specified.",
            "&b/&cGameMode <&bmode&c> [&bP&c]&7: Changes your gamemode!",
            "                 &b[ &c/gm <mode> [player] &b]",
            "&b/&cSmite [&bP&c]&7: Thor is watching... &b[ &c/thor&b,&c /lightning &b].",
            "&b/&cBurn [&bP&c]&7: Burn yourself or the specified player.",
            "&b/&cTeleport <&bP&c> [&bP&c]&7: Teleports you or the specified players.",
            "&b/&cInvSee [&bP&c]&7: Let's you look inside of a players inventory.",
            "&b/&cFly [&bP&c]&7: Makes you or the named player take flight!",
            "&b/&cVanish [&bP&c]&7: Makes you or the named player disappear!",
    };

    public void sendHelp(CommandSender sender, int page) {

        ArrayList<String> message = new ArrayList<>();
        message.add("");
        message.add(format(" &9-=================- &6&lHelp &r&7(&6&l" + (page+1) + "&r&7/&6&l" + pages + "&r&7) &9-=================- "));

        int start = page * PAGE_LENGTH;
        int end = start + PAGE_LENGTH;
        for (int i = start; i < end; i++) if (i < commands.length) message.add(format(" &9|| &r" + commands[i]));

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

        }

        return true;

    }
}
