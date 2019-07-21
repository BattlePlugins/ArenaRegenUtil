package org.battleplugins.arenaregenutil.worldedit;

import org.battleplugins.arenaregenutil.AbstractArenaRegenHandler;
import org.battleplugins.arenaregenutil.ArenaRegenHandler;
import org.battleplugins.arenaregenutil.region.ArenaSelection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * A wrapper for asynchronous FastAsyncWorldEdit pastes.
 * NOTE: It is NOT safe to use this wrapper if FastAsyncWorldEdit
 * is not installed. This is untested with other async WorldEdit
 * plugins like AsyncWorldEdit.
 *
 * @author Redned
 */
public class FAWERegenWrapper extends AbstractArenaRegenHandler {

    private ArenaRegenHandler handler;

    public FAWERegenWrapper(ArenaRegenHandler handler) {
        this.handler = handler;
    }

    @Override
    public void saveSchematic(Player player, String schematic) {
        handler.saveSchematic(player, schematic);
    }

    @Override
    public void pasteSchematic(String worldName, String schematic, Location loc) {
        Bukkit.getScheduler().runTaskAsynchronously(WorldEditRegenController.getWorldEditPlugin(), () -> {
            handler.pasteSchematic(worldName, schematic, loc);
        });
    }

    @Override
    public ArenaSelection getSelection(Player player) {
        return handler.getSelection(player);
    }
}
