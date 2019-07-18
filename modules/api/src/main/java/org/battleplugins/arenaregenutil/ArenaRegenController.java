package org.battleplugins.arenaregenutil;

import org.battleplugins.arenaregenutil.region.ArenaRegion;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class ArenaRegenController {

    private static Plugin plugin;

    public static void setPlugin(Plugin plugin) {
        ArenaRegenController.plugin = plugin;
    }

    public static void regenArea(List<Block> oldBlocks, int tickInterval, int delay) {
        if (plugin == null)
            return;

        // TODO: Implement code from handler
    }

    public static void pasteSchematic(ArenaRegion region, boolean pasteInstant) {
        // TODO: Implement code from handler
    }

    public static void pasteSchematic(String worldName, String id, boolean pasteInstant) {
        // TODO: Implement code from handler
    }
}
