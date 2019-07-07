package io.github.coachluck.events;

import io.github.coachluck.EssentialServer;
import org.bukkit.event.Listener;

public class PlayerJoinMessage implements Listener {

    EssentialServer plugin;

    public PlayerJoinMessage(EssentialServer plugin) {
        this.plugin = plugin;
    }


}
