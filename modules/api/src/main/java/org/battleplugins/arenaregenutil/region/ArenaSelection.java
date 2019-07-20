package org.battleplugins.arenaregenutil.region;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * Stores basic selection info for block restoration.
 *
 * @author Redned
 */
public class ArenaSelection {

    private Location min;
    private Location max;

    /**
     * Constructs a new ArenaSelection instance
     *
     * @param min the minimum point of the region
     * @param max the maximum point of the region
     */
    public ArenaSelection(Location min, Location max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Returns the world the region is located in
     *
     * @return the world the region is located in
     */
    public World getWorld() {
        return min.getWorld();
    }

    /**
     * Returns the minimum point of the region
     *
     * @return the minimum point of the region
     */
    public Location getMinimumPoint() {
        return min;
    }

    /**
     * Returns the maximum point of the region
     *
     * @return the maximum point of the region
     */
    public Location getMaximumPoint() {
        return max;
    }
}
