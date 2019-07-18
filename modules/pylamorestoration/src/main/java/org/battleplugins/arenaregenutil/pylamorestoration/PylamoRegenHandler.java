package org.battleplugins.arenaregenutil.pylamorestoration;

import de.pylamo.pylamorestorationsystem.Commands.CreateRegionCommand;
import de.pylamo.pylamorestorationsystem.Commands.DeleteRegionCommand;
import de.pylamo.pylamorestorationsystem.Commands.RestoreCommand;
import org.battleplugins.arenaregenutil.AbstractArenaRegenHandler;
import org.battleplugins.arenaregenutil.ArenaRegenController;
import org.battleplugins.arenaregenutil.RegenPlugin;
import org.battleplugins.arenaregenutil.region.ArenaSelection;
import org.battleplugins.arenaregenutil.worldedit.WorldEditRegenController;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Regen handler for PylamoRestorationSystem
 * This plugin requires WorldEdit to function (mainly
 * for selections), so there are a few calls for WorldEdit
 * classes in here.
 *
 * @author Redned
 */
public class PylamoRegenHandler extends AbstractArenaRegenHandler {

    @Override
    public void saveSchematic(Player player, String schematic) {
        ArenaSelection selection = getSelection(player);
        DeleteRegionCommand.deleteRegionCommand(player, new String[]{"delete", schematic});
        CreateRegionCommand.createRegion(selection.getMinimumPoint(), selection.getMaximumPoint(), schematic);
    }

    @Override
    public void pasteSchematic(String worldName, String id, String schematic, Location loc, boolean pasteInstant) {
        RestoreCommand.restoreCommand(Bukkit.getConsoleSender(), new String[]{"restore", schematic});
    }

    @Override
    public ArenaSelection getSelection(Player player) {
        return ArenaRegenController.getSelection(RegenPlugin.WORLDEDIT, player);
    }
}
