package org.battleplugins.arenaregenutil.region;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * Stores basic region info for block restoration
 *
 * @author Redned
 */
public interface ArenaRegion {

    /**
     * Returns if the region is valid
     *
     * @return if the region is valid
     */
    boolean isValid();

    /**
     * Returns the world the region is located in
     *
     * @return the world the region is located in
     */
    World getWorld();

    /**
     * Returns the id of the region
     *
     * @return the id of the region
     */
    String getID();
}
