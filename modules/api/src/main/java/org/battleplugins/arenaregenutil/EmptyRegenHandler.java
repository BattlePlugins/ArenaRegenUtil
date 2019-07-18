package org.battleplugins.arenaregenutil;

import org.battleplugins.arenaregenutil.region.ArenaSelection;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class EmptyRegenHandler extends AbstractArenaRegenHandler {

    @Override
    public void saveSchematic(Player player, String schematic) {
        /* do nothing */
    }

    @Override
    public void pasteSchematic(String worldName, String id, String schematic, Location loc, boolean pasteInstant) {
        /* do nothing */
    }

    @Override
    public ArenaSelection getSelection(Player player) {
        return null;
    }
}
