package pw.ian.albkit.command.parser;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * A wrapper around a String which allows for parsing of many primitive data
 * types as well as providing methods to check whether the argument is a valid
 * form of said primitive types
 *
 * @author Ollie
 */
public class ChatSection {
    /**
     * The raw string for the argument wrapped by this ChatSection object
     */
    private final String raw;

    /**
     * Creates a new ChatSection, using the given String argument as a raw
     * string
     *
     * @param arg The raw string for this ChatSection
     */
    public ChatSection(final String arg) {
        this.raw = arg;
    }

    /**
     * Gets the raw string this ChatSection wraps
     *
     * @return This ChatSection's raw String value
     */
    public String get() {
        return rawString();
    }

    /**
     * Gets the raw string this ChatSection wraps
     *
     * @return This ChatSection's raw String value
     */
    public String rawString() {
        return raw;
    }

    /**
     * Returns this ChatSection's value parsed as an int
     *
     * @return This ChatSection's value parsed as an int
     * @throws NumberFormatException if the value isn't an int
     */
    public int asInt() {
        return Integer.parseInt(raw);
    }

    /**
     * Returns this ChatSection's value parsed as a double
     *
     * @return This ChatSection's value parsed as a double
     * @throws NumberFormatException if the value isn't a double
     */
    public double asDouble() {
        return Double.parseDouble(raw);
    }

    /**
     * Returns this ChatSection's value parsed as a float
     *
     * @return This ChatSection's value parsed as a float
     * @throws NumberFormatException if the argument isn't a float
     */
    public float asFloat() {
        return Float.parseFloat(raw);
    }

    /**
     * Returns this ChatSection's value parsed as a long
     *
     * @return This ChatSection's value parsed as a long
     * @throws NumberFormatException if the value isn't a long
     */
    public long asLong() {
        return Long.parseLong(raw);
    }

    /**
     * Returns this ChatSection's value parsed as a short
     *
     * @return This ChatSection's value parsed as a short
     * @throws NumberFormatException if the value isn't a short
     */
    public short asShort() {
        return Short.parseShort(raw);
    }

    /**
     * Returns this ChatSection's value parsed as a boolean
     *
     * @return This ChatSection's value parsed as a boolean. Note that if the
     * value of the ChatSection isn't a valid boolean, false is returned
     */
    public Boolean asBoolean() {
        return Boolean.valueOf(raw);
    }

    /**
     * Gets the player by the name of the argument, or null if the player isn't
     * online
     *
     * @return The player by the name of the raw raw - null if (s)he isn't online
     */
    public Player asPlayer() {
        return Bukkit.getPlayer(raw);
    }

    /**
     * Gets the offline player by the name of the argument
     *
     * @return The offline player by the name of the raw raw
     */
    public OfflinePlayer asOfflinePlayer() {
        return Bukkit.getOfflinePlayer(raw);
    }

    /**
     * Gets the material whose name is the same as the raw value (ignores case)
     *
     * @return The material whose name is the same as the raw value, or null if
     * no material has that name
     */
    public Material asMaterialFromName() {
        return Material.getMaterial(raw.toUpperCase());
    }

    /**
     * Gets the material whose id is the same as the raw value
     *
     * @return The material whose id is the same as the raw value
     * @throws NumberFormatException If the value isn't a valid int
     */
    public Material asMaterialFromId() {
        return Material.getMaterial(asInt());
    }

    /**
     * Checks whether this ChatSection's value can be parsed as an integer
     *
     * @return Whether this ChatSection's value can be parsed as an integer
     */
    public boolean isInt() {
        try {
            asInt();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks whether this ChatSection's value can be parsed as a double
     *
     * @return Whether this ChatSection's value can be parsed as a double
     */
    public boolean isDouble() {
        try {
            asDouble();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks whether this ChatSection's value can be parsed as a float
     *
     * @return Whether this ChatSection's value can be parsed as a float
     */
    public boolean isFloat() {
        try {
            asFloat();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks whether this ChatSection's value can be parsed as a long
     *
     * @return Whether this ChatSection's value can be parsed as a long
     */
    public boolean isLong() {
        try {
            asLong();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks whether this ChatSection's value can be parsed as a short
     *
     * @return Whether this ChatSection's value can be parsed as a short
     */
    public boolean isShort() {
        try {
            asShort();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks whether this ChatSection's value can be parsed as a boolean
     *
     * @return Whether this ChatSection's value can be parsed as a boolean
     */
    public boolean isBoolean() {
        return raw.equals("true") || raw.equals("false");
    }

    /**
     * Checks whether this ChatSection's value can be parsed as a player
     *
     * @return Whether this ChatSection's value can be parsed as a player
     */
    public boolean isPlayer() {
        return asPlayer() != null;
    }

    /**
     * Checks whether this ChatSection's value can be parsed as an offline
     * player
     *
     * @return Whether this ChatSection's value can be parsed as an offline
     * player
     */
    public boolean isOfflinePlayer() {
        return asOfflinePlayer() != null;
    }

    /**
     * Checks whether this ChatSection's value can be parsed as a material
     * from it's name
     *
     * @return Whether this ChatSection's value can be parsed as a material
     * from it's name
     */
    public boolean isMaterialName() {
        return asMaterialFromName() != null;
    }

    /**
     * Checks whether this ChatSection's value can be parsed as a material
     * from it's ID
     *
     * @return Whether this ChatSection's value can be parsed as a material
     * from it's ID
     */
    public boolean isMaterialId() {
        try {
            return asMaterialFromId() != null;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
