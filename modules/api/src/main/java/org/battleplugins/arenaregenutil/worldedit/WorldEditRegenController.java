package org.battleplugins.arenaregenutil.worldedit;

import mc.euro.version.FieldTester;
import mc.euro.version.Version;
import mc.euro.version.VersionFactory;
import org.battleplugins.arenaregenutil.AbstractArenaRegenHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;

/**
 * We want to always reference an abstraction of WorldGuard and have the
 * actual implementation decided at runtime: v5, v6, v7.
 *
 * This class was a conversion from the static wrapper WorldGuardUtil.
 * Converted to an abstraction so that our implementation can vary at runtime.
 *
 * @author alkarin, Nikolai, Redned
 */
public class WorldEditRegenController {

    private static Plugin wep;
    private static AbstractArenaRegenHandler handler = newInstance();

    /**
     * Instantiates: org.battleplugins.arenaregenutil.worldedit.{version}.WorldEditRegenHandler
     *
     * @return the proper WorldEditRegionHandler version for the server's version
     */
    public static AbstractArenaRegenHandler newInstance() {
        Version<Plugin> worldEdit = VersionFactory.getPluginVersion("WorldEdit");

        // Since FAWE doesn't need WorldEdit to function, we need to check if FAWE is installed instead
        if (Bukkit.getPluginManager().isPluginEnabled("FastAsyncWorldEdit")) {
            wep = Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit");
        } else if (Bukkit.getPluginManager().isPluginEnabled("WorldEdit")) {
            wep = Bukkit.getPluginManager().getPlugin("WorldEdit");
        }

        boolean isInitialized = (wep != null && FieldTester.isInitialized(wep));
        if (!isInitialized)
            return null;

        AbstractArenaRegenHandler regenHandler = null;
        if (worldEdit.isCompatible("7")) {
            regenHandler = instantiate("v7");
        } else if (worldEdit.isLessThan("7") && worldEdit.isCompatible("6")) {
            regenHandler = instantiate("v6");
        } else if (worldEdit.isLessThan("6") && worldEdit.isCompatible("5")) {
            regenHandler = instantiate("v5");
        }

        if (regenHandler != null && Bukkit.getPluginManager().isPluginEnabled("FastAsyncWorldEdit")) {
            return new FAWERegenWrapper(regenHandler);
        }

        return regenHandler;
    }

    private static AbstractArenaRegenHandler instantiate(String version) {
        String classPackage = "org.battleplugins.arenaregenutil.worldedit." + version + ".WorldEditRegenHandler";
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

    public static Plugin getWorldEditPlugin() {
        return wep;
    }
}
