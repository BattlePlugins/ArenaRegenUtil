package org.battleplugins.arenaregenutil.worldedit;

import mc.euro.version.FieldTester;
import mc.euro.version.Version;
import mc.euro.version.VersionFactory;
import org.battleplugins.arenaregenutil.AbstractArenaRegenHandler;
import org.battleplugins.arenaregenutil.region.ArenaRegion;
import org.battleplugins.arenaregenutil.region.ArenaSelection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
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

    private static Plugin wep = (Plugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
    private static AbstractArenaRegenHandler handler = newInstance();

    /**
     * Instantiates: org.battleplugins.arenaregenutil.worldedit.{version}.WorldEditRegenHandler
     *
     * @return the proper WorldEditRegionHandler version for the server's version
     */
    public static AbstractArenaRegenHandler newInstance() {
        Version<Plugin> worldEdit = VersionFactory.getPluginVersion("WorldEdit");
        boolean isInitialized = FieldTester.isInitialized(wep);
        if (!isInitialized)
            return null;

        if (worldEdit.isCompatible("7")) {
            return instantiate("v7");
        } else if (worldEdit.isLessThan("7") && worldEdit.isCompatible("6")) {
            return instantiate("v6");
        } else if (worldEdit.isLessThan("6") && worldEdit.isCompatible("v6")) {
            return instantiate("v5");
        }

        return null;
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
