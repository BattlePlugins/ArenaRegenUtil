package org.battleplugins.arenaregenutil.rollbackcore;

import mc.euro.version.FieldTester;
import org.battleplugins.arenaregenutil.AbstractArenaRegenHandler;
import org.battleplugins.arenaregenutil.worldedit.WorldEditRegenController;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;

/**
 * Controller for RollbackCore
 *
 * @author Redned
 */
public class RollbackCoreRegenController {

    private static Plugin rollbackPlugin;
    private static AbstractArenaRegenHandler handler = newInstance();

    /**
     * Instantiates: org.battleplugins.arenaregenutil.rollbackcore.RollbackCoreRegenHandler
     *
     * @return the proper RollbackCore version for the server's version
     */
    public static AbstractArenaRegenHandler newInstance() {
        if (Bukkit.getPluginManager().isPluginEnabled("RollbackCore")) {
            rollbackPlugin = Bukkit.getPluginManager().getPlugin("RollbackCore");
        }

        boolean isInitialized = (rollbackPlugin != null && FieldTester.isInitialized(rollbackPlugin));
        if (!isInitialized)
            return null;

        // WorldEdit needs to be installed for RollbackCore selections
        if (WorldEditRegenController.getWorldEditPlugin() == null) {
            return null;
        }

        if (!rollbackPlugin.getDescription().getVersion().startsWith("3.")) {
            Bukkit.getLogger().warning("Could not instantiate RollbackCore with version " + rollbackPlugin.getDescription().getVersion() + "! Must have RollbackCore 3.0.0+ for support to be enabled!");
            return null;
        }

        String classPackage = "org.battleplugins.arenaregenutil.rollbackcore.RollbackCoreRegenHandler";
        AbstractArenaRegenHandler handler = null;
        Class<?>[] args = {};
        Class clazz = null;
        Constructor con = null;
        try {
            clazz = Class.forName(classPackage);
            con = clazz.getConstructor(args);
            handler = (AbstractArenaRegenHandler) con.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return handler;
    }
}
