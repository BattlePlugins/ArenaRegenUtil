package org.battleplugins.arenaregenutil.worldedit;

import org.battleplugins.arenaregenutil.AbstractArenaRegenHandler;
import org.battleplugins.arenaregenutil.region.ArenaRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

/**
 * Controller for PylamoRestorationSystem
 *
 * @author Redned
 */
public class PylamoRegenController {

    private static AbstractArenaRegenHandler handler = newInstance();

    /**
     * Instantiates: org.battleplugins.arenaregenutil.pylamorestoration.PylamoRegenHandler
     *
     * @return the proper PylamoRegenHandler version for the server's version
     */
    public static AbstractArenaRegenHandler newInstance() {
        String classPackage = "org.battleplugins.arenaregenutil.pylamorestoration.PylamoRegenHandler";
        AbstractArenaRegenHandler handler = null;
        Class<?>[] args = {};
        Class clazz = null;
        Constructor con = null;
        try {
            clazz = Class.forName(classPackage);
            con = clazz.getConstructor(args);
            handler = (AbstractArenaRegenHandler) con.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return handler;
    }


    /**
     * Saves a schematic with the specified name
     *
     * @param player the player saving the schematic
     * @param schematic the schematic being saved
     */
    public static void saveSchematic(Player player, String schematic) {
        if (handler == null)
            return;

        handler.saveSchematic(player, schematic);
    }

    /**
     * Pastes a schematic with the specified {@link ArenaRegion}
     *
     * @param region the region to paste the schematic in
     * @param schematic the name of the schematic
     * @param loc the location to paste the schematic at
     * @param pasteInstant if the schematic should be pasted instantly
     */
    public static void pasteSchematic(ArenaRegion region, String schematic, Location loc, boolean pasteInstant) {
        if (handler == null)
            return;

        handler.pasteSchematic(region, schematic, loc, pasteInstant);
    }

    /**
     * Pastes a schematic with the given world name and id
     *
     * @param worldName the name of the world
     * @param id the id/name of the schematic
     * @param schematic the name of the schematic
     * @param loc the location to paste the schematic at
     * @param pasteInstant if the schematic should be pasted instantly
     */
    public static void pasteSchematic(String worldName, String id, String schematic, Location loc, boolean pasteInstant) {
        if (handler == null)
            return;

        handler.pasteSchematic(worldName, id, schematic, loc, pasteInstant);
    }
}
