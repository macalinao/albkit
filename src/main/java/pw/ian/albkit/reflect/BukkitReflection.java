package pw.ian.albkit.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

/**
 *
 * @author ian
 */
public class BukkitReflection {

    public static final String NMS_PACKAGE;
    public static final String CB_PACKAGE;

    static {
        NMS_PACKAGE = getVersionedPackage("net.minecraft.server");
        CB_PACKAGE = getVersionedPackage("org.bukkit.craftbukkit");
    }

    public static Class<?> getNMSClass(String name) throws ReflectException {
        return getClass(NMS_PACKAGE, name);
    }

    public static Class<?> getCraftBukkitClass(String name) throws ReflectException {
        return getClass(CB_PACKAGE, name);
    }

    public static Class<?> getClass(String pkg, String name) throws ReflectException {
        try {
            return Class.forName(pkg + "." + name);
        } catch (ClassNotFoundException e) {
            throw new ReflectException("Could not find class \"" + name + "\" in package \"" + pkg + "\".", e);
        }
    }

    public static String getVersionedPackage(String base) throws ReflectException {
        for (Package pkg : Package.getPackages()) {
            String name = pkg.getName();
            if (name.startsWith(base + ".v")) {
                int index = (base + ".v").length();
                String after = name.substring(index);
                return name.substring(0, (after.contains(".") ? after.indexOf(".") : after.length()) + index);
            }
        }

        // Unversioned package or we're in a development environment.
        return base;
    }

    public static Entity spawnEntity(Location loc, EntityType type) {
        Class<?> craftWorld = getCraftBukkitClass("CraftWorld");
        try {
            Method method = craftWorld.getMethod("spawn", Location.class, Class.class, SpawnReason.class);
            return (Entity) method.invoke(loc.getWorld(), loc, type.getEntityClass(), SpawnReason.NATURAL);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(BukkitReflection.class.getName()).log(Level.SEVERE, "Method is DNE.", ex);
        } catch (SecurityException ex) {
            Logger.getLogger(BukkitReflection.class.getName()).log(Level.SEVERE, "Insecure thing", ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BukkitReflection.class.getName()).log(Level.SEVERE, "Illegal access", ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(BukkitReflection.class.getName()).log(Level.SEVERE, "Illegal argument", ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(BukkitReflection.class.getName()).log(Level.SEVERE, "Invocation target wrong", ex);
        }
        return null;
    }

    private BukkitReflection() {
    }
}
