package org.battleplugins.arenaregenutil;

import org.battleplugins.arenaregenutil.region.ArenaRegion;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * Main regen API handler
 *
 * @author Redned
 */
public interface ArenaRegenHandler {

    /**
     * Regenerates the specified blocks over a period of time.
     * Should be called before the blocks are modified, otherwise nothing
     * will actually happen.
     *
     * @param plugin the plugin initiating the regen
     * @param oldBlocks the blocks to restore
     * @param tickInterval the interval of the restoration
     * @param delay the amount of time (in ticks) until task will run
     */
    void regenArea(Plugin plugin, List<Block> oldBlocks, int tickInterval, int delay);

    /**
     * Pastes a schematic with the specified {@link ArenaRegion}
     *
     * @param region the region to paste the schematic in
     * @param pasteInstant if the schematic should be pasted instantly
     */
    void pasteSchematic(ArenaRegion region, boolean pasteInstant);

    /**
     * Pastes a schematic with the given world name and id
     *
     * @param worldName the name of the world
     * @param id the id/name of the schematic
     * @param pasteInstant if the schematic should be pasted instantly
     */
    void pasteSchematic(String worldName, String id, boolean pasteInstant);
}
