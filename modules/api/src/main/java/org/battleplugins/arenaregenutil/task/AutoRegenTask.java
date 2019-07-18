package org.battleplugins.arenaregenutil.task;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Task for regenerating a list of blocks over a period of time
 *
 * @author Redned
 */
public class AutoRegenTask implements Runnable {

    private Plugin plugin;
    private List<BlockState> blockStates;

    private int tickInterval;

    /**
     * Constucts a new AutoRegenTask instance
     *
     * @param plugin the plugin initiating the regen
     * @param blocks the block to restore
     * @param tickInterval the interval of the restoration
     */
    public AutoRegenTask(Plugin plugin, List<Block> blocks, int tickInterval) {
        this.plugin = plugin;
        List<BlockState> blockStates = new ArrayList<BlockState>();
        blocks.forEach(block -> blockStates.add(block.getState()));

        this.blockStates = blockStates;
        this.tickInterval = tickInterval;
    }

    @Override
    public void run() {
        for (BlockState state : blockStates) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {

                // Dont update the physics so blocks like grass don't vanish
                state.update(true, false);
            }, new Random().nextInt(10 * 20) + tickInterval);
        }
    }
}
