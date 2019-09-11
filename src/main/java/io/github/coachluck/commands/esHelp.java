package io.github.coachluck.commands;

import io.github.coachluck.EssentialServer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class esHelp implements CommandExecutor {
    private final EssentialServer plugin;
    public esHelp(EssentialServer plugin) {
        this.plugin = plugin;
    }

    private final int PAGE_LENGTH = 6;
    private int pages;

    private String[] commands = {
            "Help: Get help about commands.",
            "Whisper: Send a private message to another player.",
            "Reply: Reply to a private message from another player.",
            "Money: Check how much money you currently have.",
            "command1: description1",
            "command2: description2",
            "command3: description3",
            "command4: description4",
    };

    public void sendHelp(CommandSender sender, int page) {

        ArrayList<String> message = new ArrayList<String>();
        message.add("");
        message.add(" §9-==============-  §lHelp (" + (page+1) + "/" + pages + ")  §9-==============- ");

        int start = page * PAGE_LENGTH;
        int end = start + PAGE_LENGTH;
        for (int i = start; i < end; i++) if (i < commands.length) message.add("§9" + commands[i]);

        sender.sendMessage(message.toArray(new String[message.size()]));

    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("help")) {

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

        return false;

    }
}
