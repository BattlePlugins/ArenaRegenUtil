package org.battleplugins.arenaregenutil.rollbackcore;

import net.shadowxcraft.rollbackcore.Copy;
import net.shadowxcraft.rollbackcore.Paste;
import org.battleplugins.arenaregenutil.AbstractArenaRegenHandler;
import org.battleplugins.arenaregenutil.ArenaRegenController;
import org.battleplugins.arenaregenutil.RegenPlugin;
import org.battleplugins.arenaregenutil.region.ArenaSelection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Regen handler for RollbackCore
 * This plugin requires WorldEdit to function (mainly
 * for selections), so there are a few calls for WorldEdit
 * classes in here.
 * @author Redned
 */
public class RollbackCoreRegenHandler extends AbstractArenaRegenHandler {

    @Override
    public void saveSchematic(Player player, String schematic) {
        ArenaSelection selection = getSelection(player);
        new Copy(selection.getMinimumPoint(), selection.getMaximumPoint(), ArenaRegenController.getPlugin() + "/saves/schematics/", player, "");
    }

    @Override
    public void pasteSchematic(String worldName, String schematic, Location loc) {
        new Paste(loc, ArenaRegenController.getPlugin() + "/saves/schematics/" + schematic, Bukkit.getConsoleSender(), true, false, "");
    }

    @Override
    public ArenaSelection getSelection(Player player) {
        return ArenaRegenController.getSelection(RegenPlugin.WORLDEDIT, player);
    }
}
