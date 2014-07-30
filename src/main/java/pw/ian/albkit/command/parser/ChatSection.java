package pw.ian.albkit.command.parser;

/**
 * A wrapper around a String which allows for parsing of many primitive data
 * types as well as providing methods to check whether the argument is a valid
 * form of said primitive types
 *
 * @author Ollie
 */
public class ChatSection {
    /**
     * The raw argument
     */
    private final String arg;

    public ChatSection(final String arg) {
        this.arg = arg;
    }

    public String rawString() {
        return arg;
    }

    public int asInt() {
        return Integer.parseInt(arg);
    }

    public double asDouble() {
        return Double.parseDouble(arg);
    }

    public float asFloat() {
        return Float.parseFloat(arg);
    }

    public long asLong() {
        return Long.parseLong(arg);
    }

    public short asShort() {
        return Short.parseShort(arg);
    }

    public boolean asBoolean() {
        return Boolean.parseBoolean(arg);
    }

    public boolean isInt() {
        try {
            asInt();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isDouble() {
        try {
            asDouble();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isFloat() {
        try {
            asFloat();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isLong() {
        try {
            asLong();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isShort() {
        try {
            asShort();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isBoolean() {
        return arg.equals("true") || arg.equals("false");
    }
}
