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
    private int pages = 2;

    private String[] commands = {

            "&a/&cesHelp <&b#&c>&7: &aGet help about commands. &c<>&a is optional.",
            "&a/&csetSpawn&7: &aSets the spawn for the server.",
            "&a/&cSpawn <&bP&c>&7: &aTeleports you or the player to spawn.",
            "&a/&cClear <&bP&c>&7: &aClears you or the players inventory.",
            "           &a<&c/ci&a, /clearinv&a, /clearinventory&a>",
            "&a/&cFeed <&bP&c>&7: &aRestores your or the players hunger.",
            "&a/&cHeal <&bP&c>&7: &aHeal yourself or the player.",
            "&a/&cGod <&bP&c>&7: &aMakes your completely invincible.",
            "&a/&cKill <&bP&c>&7: &aKill yourself or the player.",
            "&a/&cSmite <&bP&c>&7: &aThor is watching... <&c/thor&a,&c /lightning&a>.",
            "&a/&cBurn <&bP&c>&7: &aBurn yourself or the specified player.",
            "&a/&cFly <&bP&c>&7: &aMakes you or the named player take flight!",
            "&a/&cVanish <&bP&c>&7: &aMakes you or the named player disappear!",
    };

    public void sendHelp(CommandSender sender, int page) {

        ArrayList<String> message = new ArrayList<>();
        message.add("");
        message.add(format(" &9-===============- &6&lHelp &r&7(&6&l" + (page+1) + "&r&7/&6&l" + pages + "&r&7) &9-===============- "));

        int start = page * PAGE_LENGTH;
        int end = start + PAGE_LENGTH;
        for (int i = start; i < end; i++) if (i < commands.length) message.add(format(" &9|| &r" + commands[i]));

        sender.sendMessage(message.toArray(new String[message.size()]));

    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("eshelp")) {

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
