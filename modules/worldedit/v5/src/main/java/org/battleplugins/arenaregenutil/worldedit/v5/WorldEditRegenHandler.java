package org.battleplugins.arenaregenutil.worldedit.v5;

import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalConfiguration;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.ServerInterface;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitCommandSender;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.commands.SchematicCommands;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.schematic.SchematicFormat;
import org.battleplugins.arenaregenutil.AbstractArenaRegenHandler;
import org.battleplugins.arenaregenutil.region.ArenaSelection;
import org.battleplugins.arenaregenutil.worldedit.WorldEditRegenController;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

/**
 * Regen handler for WorldEdit v5
 *
 * @author alkarin, Redned
 */
public class WorldEditRegenHandler extends AbstractArenaRegenHandler {

    @Override
    public void saveSchematic(Player player, String schematic) {
        CommandContext cc;
        WorldEditPlugin wep = (WorldEditPlugin) WorldEditRegenController.getWorldEditPlugin();
        final LocalSession session = wep.getSession(player);
        final BukkitPlayer lPlayer = wep.wrapPlayer(player);
        EditSession editSession = session.createEditSession(lPlayer);

        ArenaSelection selection = getSelection(player);
        Vector min = BukkitUtil.toVector(selection.getMinimumPoint());
        Vector max = BukkitUtil.toVector(selection.getMaximumPoint());

        CuboidClipboard clipboard = new CuboidClipboard(
                max.subtract(min).add(new Vector(1, 1, 1)),
                min, new Vector(0, 0, 0));
        clipboard.copy(editSession);
        session.setClipboard(clipboard);

        try {
            SchematicCommands sc = new SchematicCommands(wep.getWorldEdit());
            String args2[] = {"save", "mcedit", schematic};
            cc = new CommandContext(args2);
            sc.save(cc, session, lPlayer, editSession);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        return;
    }

    @Override
    public void pasteSchematic(String worldName, String id, String schematic, Location loc) {
        CommandSender sender = Bukkit.getConsoleSender();
        World world = Bukkit.getWorld(worldName);
        if (world == null)
            return;

        CommandContext cc;
        String args[] = {"load", schematic};
        final WorldEditPlugin wep = (WorldEditPlugin) WorldEditRegenController.getWorldEditPlugin();
        final WorldEdit we = wep.getWorldEdit();
        LocalPlayer bcs = new ConsolePlayer(wep, wep.getServerInterface(), sender, world);

        final LocalSession session = wep.getWorldEdit().getSession(bcs);
        session.setUseInventory(false);
        EditSession editSession = session.createEditSession(bcs);
        try {
            cc = new CommandContext(args);
            loadAndPaste(cc, we, session, bcs, editSession, loc);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void loadAndPaste(CommandContext args, WorldEdit we,
                                LocalSession session, LocalPlayer player, EditSession editSession, Location loc) {

        LocalConfiguration config = we.getConfiguration();

        String filename = args.getString(0);
        File dir = we.getWorkingDirectoryFile(config.saveDir);
        File f;
        try {
            f = we.getSafeOpenFile(player, dir, filename, "schematic", "schematic");
            String filePath = f.getCanonicalPath();
            String dirPath = dir.getCanonicalPath();

            if (!filePath.substring(0, dirPath.length()).equals(dirPath)) {
                player.printError("Schematic could not read or it does not exist.");
                return;
            }
            SchematicFormat format = SchematicFormat.getFormat(f);
            if (format == null) {
                player.printError("Unknown schematic format for file" + f);
                return;
            }

            if (!filePath.substring(0, dirPath.length()).equals(dirPath)) {
                player.printError("Schematic could not read or it does not exist.");
            } else {
                session.setClipboard(format.load(f));
                // WorldEdit.logger.info(player.getName() + " loaded " + filePath);
                // print(player,filePath + " loaded");
            }
            session.getClipboard().paste(editSession, new Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), false, true);
            // WorldEdit.logger.info(player.getName() + " pasted schematic" + filePath +"  at " + pos);
        } catch (DataException e) {
            player.printError("Load error: " + e.getMessage());
        } catch (IOException e) {
            player.printError("Schematic could not read or it does not exist: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            player.printError("Error : " + e.getMessage());
        }
    }

    @Override
    public ArenaSelection getSelection(Player player) {
        final WorldEditPlugin wep = (WorldEditPlugin) WorldEditRegenController.getWorldEditPlugin();
        final LocalSession session = wep.getSession(player);
        final BukkitPlayer lPlayer = wep.wrapPlayer(player);

        try {
            Region region = session.getSelection(lPlayer.getWorld());
            Vector min = region.getMinimumPoint();
            Vector max = region.getMaximumPoint();

            World world = BukkitUtil.toWorld(region.getWorld());
            Location minLoc = new Location(world, min.getX(), min.getY(), min.getZ());
            Location maxLoc = new Location(world, max.getX(), max.getY(), max.getZ());
            return new ArenaSelection(minLoc, maxLoc);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public class ConsolePlayer extends BukkitCommandSender {

        LocalWorld world;

        public ConsolePlayer(WorldEditPlugin plugin, ServerInterface server, CommandSender sender, World w) {
            super(plugin, server, sender);
            world = BukkitUtil.getLocalWorld(w);
        }

        @Override
        public boolean isPlayer() {
            return true;
        }

        @Override
        public LocalWorld getWorld() {
            return world;
        }
    }
}
