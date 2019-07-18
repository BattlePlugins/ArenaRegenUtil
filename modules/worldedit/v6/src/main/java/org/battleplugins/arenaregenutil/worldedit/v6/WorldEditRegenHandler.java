package org.battleplugins.arenaregenutil.worldedit.v6;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalConfiguration;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldedit.util.io.Closer;
import com.sk89q.worldedit.world.registry.LegacyWorldData;
import org.battleplugins.arenaregenutil.AbstractArenaRegenHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Regen handler for WorldEdit v6
 *
 * @author alkarin, Redned, Paaattiii
 */
public class WorldEditRegenHandler extends AbstractArenaRegenHandler {

    private WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");

    @Override
    public void saveSchematic(Player player, String schematic) {
        LocalSession session = wep.getSession(player);
        com.sk89q.worldedit.entity.Player wePlayer = wep.wrapPlayer(player);
        EditSession editSession = session.createEditSession(wePlayer);
        Closer closer = Closer.create();
        try {
            Region region = session.getSelection(wePlayer.getWorld());
            Clipboard cb = new BlockArrayClipboard(region);
            ForwardExtentCopy copy = new ForwardExtentCopy(editSession, region, cb, region.getMinimumPoint());
            Operations.completeLegacy(copy);
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
            ClipboardWriter writer = closer.register(ClipboardFormat.SCHEMATIC.getWriter(bos));
            writer.write(cb, LegacyWorldData.getInstance());
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (IncompleteRegionException e) {
            e.printStackTrace();
        } catch (MaxChangedBlocksException e) {
            e.printStackTrace();
        } finally {
            try {
                closer.close();
            } catch (IOException ignore) {
            }
        }
    }

    // TODO: Setup pasteInstant option
    @Override
    public void pasteSchematic(String worldName, String id, String schematic, Location loc, boolean pasteInstant) {
        WorldEdit we = WorldEdit.getInstance();

        LocalConfiguration config = we.getConfiguration();
        File dir = we.getWorkingDirectoryFile(config.saveDir);
        File file = new File(dir, schematic + ".schematic");

        if (!file.exists()) {
            Bukkit.getLogger().warning("Schematic " + schematic + ".schematic does not exist!");
            return;
        }

        EditSession session = we.getEditSessionFactory().getEditSession(new BukkitWorld(loc.getWorld()), -1);
        try {
            MCEditSchematicFormat.getFormat(schematic).load(file).paste(session, new Vector(loc.getX(), loc.getY(), loc.getZ()), false);
            return;
        } catch (MaxChangedBlocksException | com.sk89q.worldedit.data.DataException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
