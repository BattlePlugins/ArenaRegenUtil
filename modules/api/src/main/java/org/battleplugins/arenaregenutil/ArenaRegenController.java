package org.battleplugins.arenaregenutil;

import org.battleplugins.arenaregenutil.region.ArenaRegion;
import org.battleplugins.arenaregenutil.region.ArenaSelection;
import org.battleplugins.arenaregenutil.worldedit.PylamoRegenController;
import org.battleplugins.arenaregenutil.worldedit.WorldEditRegenController;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for regenerating blocks
 *
 * @author Redned
 */
public class ArenaRegenController {

    private static Plugin plugin;

    private static RegenPlugin defaultPlugin = RegenPlugin.NONE;
    private static Map<RegenPlugin, ArenaRegenHandler> registeredRegenPlugins = new HashMap<>();

    /**
     * Initializes the regen plugin controller
     */
    public static void initialize() {
        registeredRegenPlugins.put(RegenPlugin.NONE, new EmptyRegenHandler());

        AbstractArenaRegenHandler worldEdit = WorldEditRegenController.newInstance();
        if (worldEdit != null) {
            registeredRegenPlugins.put(RegenPlugin.WORLDEDIT, worldEdit);
        }

        AbstractArenaRegenHandler pylamo = PylamoRegenController.newInstance();
        if (pylamo != null) {
            registeredRegenPlugins.put(RegenPlugin.PYLAMO_RESTORATION, pylamo);
        }
    }

    /**
     * Checks if the specified regen plugin is found
     *
     * @param regenPlugin the region plugin
     * @return if the specified regen plugin is found
     */
    public static boolean hasRegenPlugin(RegenPlugin regenPlugin) {
        if (!registeredRegenPlugins.containsKey(regenPlugin))
            return false;

        return true;
    }

    /**
     * Sets the default regen plugin that will be used
     *
     * @param defaultRegenPlugin the default regen plugin
     */
    public static void setDefaultRegenPlugin(RegenPlugin defaultRegenPlugin) {
        if (!registeredRegenPlugins.containsKey(defaultPlugin))
            return;

        defaultPlugin = defaultRegenPlugin;
    }

    /**
     * Sets the main plugin to run certain tasks from
     *
     * @param plugin the main plugin
     */
    public static void setPlugin(Plugin plugin) {
        ArenaRegenController.plugin = plugin;
    }

    /**
     * Regenerates the specified blocks over a period of time.
     * Should be called before the blocks are modified, otherwise nothing
     * will actually happen.
     *
     * @param oldBlocks the blocks to restore
     * @param tickInterval the interval of the restoration
     * @param delay the amount of time (in ticks) until task will run
     */
    public static void regenArea(List<Block> oldBlocks, int tickInterval, int delay) {
        regenArea(defaultPlugin, oldBlocks, tickInterval, delay);
    }

    /**
     * Regenerates the specified blocks over a period of time.
     * Should be called before the blocks are modified, otherwise nothing
     * will actually happen.
     *
     * @param regenPlugin he regen plugin to regenerate the area with
     * @param oldBlocks the blocks to restore
     * @param tickInterval the interval of the restoration
     * @param delay the amount of time (in ticks) until task will run
     */
    public static void regenArea(RegenPlugin regenPlugin, List<Block> oldBlocks, int tickInterval, int delay) {
        if (plugin == null)
            return;

        registeredRegenPlugins.get(regenPlugin).regenArea(plugin, oldBlocks, tickInterval, delay);
    }

    /**
     * Saves a schematic with the specified name
     *
     * @param player the player saving the schematic
     * @param schematic the schematic being saved
     */
    public static void saveSchematic(Player player, String schematic) {
        registeredRegenPlugins.get(defaultPlugin).saveSchematic(player, schematic);
    }

    /**
     * Saves a schematic with the specified name
     *
     * @param regenPlugin the regen plugin to save the schematic with
     * @param player the player saving the schematic
     * @param schematic the schematic being saved
     */
    public static void saveSchematic(RegenPlugin regenPlugin, Player player, String schematic) {
        registeredRegenPlugins.get(regenPlugin).saveSchematic(player, schematic);
    }

    /**
     * Pastes a schematic with the specified {@link ArenaRegion}
     *
     * @param region the region to paste the schematic in
     * @param schematic the name of the schematic
     * @param loc the location to paste the schematic at
     */
    public static void pasteSchematic(ArenaRegion region, String schematic, Location loc) {
       pasteSchematic(defaultPlugin, region, schematic, loc);
    }

    /**
     * Pastes a schematic with the specified {@link ArenaRegion}
     *
     * @param regenPlugin the regen plugin to paste the schematic from
     * @param region the region to paste the schematic in
     * @param schematic the name of the schematic
     * @param loc the location to paste the schematic at
     */
    public static void pasteSchematic(RegenPlugin regenPlugin, ArenaRegion region, String schematic, Location loc) {
        registeredRegenPlugins.get(regenPlugin).pasteSchematic(region, schematic, loc);
    }

    /**
     * Pastes a schematic with the given world name and id
     *
     * @param worldName the name of the world
     * @param schematic the name of the schematic
     * @param loc the location to paste the schematic at
     */
    public static void pasteSchematic(String worldName, String schematic, Location loc) {
        pasteSchematic(defaultPlugin, worldName, schematic, loc);
    }

    /**
     * Pastes a schematic with the given world name and id
     *
     * @param regenPlugin the regen plugin to paste the schematic from
     * @param worldName the name of the world
     * @param schematic the name of the schematic
     * @param loc the location to paste the schematic at
     */
    public static void pasteSchematic(RegenPlugin regenPlugin, String worldName, String schematic, Location loc) {
        registeredRegenPlugins.get(regenPlugin).pasteSchematic(worldName, schematic, loc);
    }

    /**
     * Returns the player's current selection
     *
     * @param player the player with the selection
     * @return the player's current selection
     */
    public static ArenaSelection getSelection(Player player) {
        return getSelection(defaultPlugin, player);
    }

    /**
     * Returns the player's current selection
     *
     * @param regenPlugin the regen plugin to get the selection from
     * @param player the player with the selection
     * @return the player's current selection
     */
    public static ArenaSelection getSelection(RegenPlugin regenPlugin, Player player) {
        return registeredRegenPlugins.get(regenPlugin).getSelection(player);
    }
}
