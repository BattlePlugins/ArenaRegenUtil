package org.battleplugins.arenaregenutil;

import org.battleplugins.arenaregenutil.region.ArenaRegion;
import org.battleplugins.arenaregenutil.region.ArenaSelection;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
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
     * Saves a schematic with the specified name
     *
     * @param player the player saving the schematic
     * @param schematic the schematic being saved
     */
    void saveSchematic(Player player, String schematic);

    /**
     * Pastes a schematic with the specified {@link ArenaRegion}
     *
     * @param region the region to paste the schematic in
     * @param loc the location to paste the schematic at
     */
    void pasteSchematic(ArenaRegion region, Location loc);

    /**
     * Pastes a schematic with the given world name and id
     *
     * @param worldName the name of the world
     * @param schematic the name of the schematic
     * @param loc the location to paste the schematic at
     */
    void pasteSchematic(String worldName, String schematic, Location loc);

    /**
     * Returns the player's current selection
     *
     * @param player the player with the selection
     * @return the player's current selection
     */
    ArenaSelection getSelection(Player player);
}
