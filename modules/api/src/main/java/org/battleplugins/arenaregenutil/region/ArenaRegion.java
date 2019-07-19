package org.battleplugins.arenaregenutil.region;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * Stores basic region info for block restoration
 *
 * @author Redned
 */
public class ArenaRegion {

    private Location min;
    private Location max;

    private String id;
    private World world;

    /**
     * Constructs a new ArenaRegion instance with empty parameters
     */
    public ArenaRegion() {

    }
    /**
     * Constructs a new ArenaRegion instance
     *
     * @param world the world the region is in
     * @param id the name/id of the region
     * @param min the minimum point of the region
     * @param max the maximum point of the region
     */
    public ArenaRegion(World world, String id, Location min, Location max) {
        this.world = world;
        this.id = id;
        this.min = min;
        this.max = max;
    }

    /**
     * Returns the world the region is located in
     *
     * @return the world the region is located in
     */
    public World getWorld() {
        return world;
    }

    /**
     * Returns the id of the region
     *
     * @return the id of the region
     */
    public String getID() {
        return id;
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
