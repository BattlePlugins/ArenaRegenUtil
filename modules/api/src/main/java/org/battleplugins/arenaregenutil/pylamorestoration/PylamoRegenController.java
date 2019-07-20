package org.battleplugins.arenaregenutil.pylamorestoration;

import org.battleplugins.arenaregenutil.AbstractArenaRegenHandler;

import java.lang.reflect.Constructor;

/**
 * Controller for PylamoRestorationSystem
 *
 * @author Redned
 */
public class PylamoRegenController {

    private static AbstractArenaRegenHandler handler = newInstance();

    /**
     * Instantiates: org.battleplugins.arenaregenutil.pylamorestoration.PylamoRegenHandler
     *
     * @return the proper PylamoRegenHandler version for the server's version
     */
    public static AbstractArenaRegenHandler newInstance() {
        String classPackage = "org.battleplugins.arenaregenutil.pylamorestoration.PylamoRegenHandler";
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
