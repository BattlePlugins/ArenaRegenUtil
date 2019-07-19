package org.battleplugins.arenaregenutil;

import org.battleplugins.arenaregenutil.region.ArenaRegion;
import org.battleplugins.arenaregenutil.task.AutoRegenTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import java.util.List;

public abstract class AbstractArenaRegenHandler implements ArenaRegenHandler {

    @Override
    public void pasteSchematic(ArenaRegion region, String schematic, Location loc) {
        pasteSchematic(region.getWorld().getName(), region.getID(), schematic, loc);
    }

    @Override
    public void regenArea(Plugin plugin, List<Block> oldBlocks, int tickInterval, int delay) {
        AutoRegenTask regenTask = new AutoRegenTask(plugin, oldBlocks, tickInterval);
        Bukkit.getScheduler().runTaskLater(plugin, regenTask, delay);
    }
}
