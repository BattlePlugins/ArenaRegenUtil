package org.battleplugins.arenaregenutil.worldedit.v7_1;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalConfiguration;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.*;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.util.io.Closer;
import com.sk89q.worldedit.util.io.file.FilenameException;
import org.battleplugins.arenaregenutil.AbstractArenaRegenHandler;
import org.battleplugins.arenaregenutil.region.ArenaSelection;
import org.battleplugins.arenaregenutil.worldedit.WorldEditRegenController;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Regen handler for WorldEdit v7.1
 *
 * @author alkarin, Redned, Paaattiii
 */
public class WorldEditRegenHandler extends AbstractArenaRegenHandler {

    @Override
    public void saveSchematic(Player player, String schematic) {
        final WorldEditPlugin wep = (WorldEditPlugin) WorldEditRegenController.getWorldEditPlugin();
        LocalSession session = wep.getSession(player);
        com.sk89q.worldedit.entity.Player wePlayer = wep.wrapPlayer(player);
        EditSession editSession = session.createEditSession(wePlayer);
        Closer closer = Closer.create();

        try {
            Region region = session.getSelection(wePlayer.getWorld());
            Clipboard cb = new BlockArrayClipboard(region);
            ForwardExtentCopy copy = new ForwardExtentCopy(editSession, region, cb, region.getMinimumPoint());
            Operations.complete(copy);
            LocalConfiguration config = wep.getWorldEdit().getConfiguration();
            File dir = wep.getWorldEdit().getWorkingDirectoryFile(config.saveDir);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    throw new IOException("Could not create directory " + config.saveDir);
                }
            }
            File schematicFile = new File(dir, schematic + ".schematic");
            schematicFile.createNewFile();

            FileOutputStream fos = closer.register(new FileOutputStream(schematicFile));
            BufferedOutputStream bos = closer.register(new BufferedOutputStream(fos));
            ClipboardWriter writer = closer.register(BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(bos));
            writer.write(cb);
        } catch (IOException | WorldEditException ex) {
            ex.printStackTrace();
        } finally {
            try {
                closer.close();
                editSession.flushSession();
            } catch (IOException ignore) {
            }
        }
    }

    @Override
    public void pasteSchematic(String worldName, String schematic, Location loc) {
        WorldEdit we = WorldEdit.getInstance();

        LocalConfiguration config = we.getConfiguration();
        File dir = we.getWorkingDirectoryFile(config.saveDir);
        File file = null;
        try {
            file = we.getSafeOpenFile(null, dir, schematic, BuiltInClipboardFormat.SPONGE_SCHEMATIC.getPrimaryFileExtension(), ClipboardFormats.getFileExtensionArray());
        } catch (FilenameException ex) {
            ex.printStackTrace();
        }

        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            Clipboard clipboard = reader.read();

            try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(BukkitAdapter.adapt(loc.getWorld()), -1)) {
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(BukkitAdapter.asBlockVector(loc))
                        .ignoreAirBlocks(false)
                        .build();
                Operations.complete(operation);
            } catch (WorldEditException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ArenaSelection getSelection(Player player) {
        final WorldEditPlugin wep = (WorldEditPlugin) WorldEditRegenController.getWorldEditPlugin();
        final LocalSession session = wep.getSession(player);
        final BukkitPlayer lPlayer = wep.wrapPlayer(player);

        try {
            Region region = session.getSelection(lPlayer.getWorld());
            BlockVector3 min = region.getMinimumPoint();
            BlockVector3 max = region.getMaximumPoint();

            World world = BukkitAdapter.adapt(region.getWorld());
            Location minLoc = new Location(world, min.getX(), min.getY(), min.getZ());
            Location maxLoc = new Location(world, max.getX(), max.getY(), max.getZ());
            return new ArenaSelection(minLoc, maxLoc);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
