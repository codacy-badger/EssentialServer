package io.github.coachluck.warps;

import org.bukkit.Location;
import org.bukkit.Sound;

public class WarpHolder {

    private Location location;
    private Sound warpSound;
    private String warpMessage;

    public WarpHolder(Location location, Sound warpSound, String warpMessage) {
        this.location = location;
        this.warpSound = warpSound;
        this.warpMessage = warpMessage;
    }

    public Location getLocation() {
        return location;
    }

    public Sound getWarpSound() {
        return warpSound;
    }

    public String getWarpMessage() {
        return warpMessage;
    }
}
