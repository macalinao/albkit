package pw.ian.albkit.command.parser;

import pw.ian.albkit.command.parser.parameter.Parameter;
import pw.ian.albkit.command.parser.parameter.Params;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple and easy to use method of parsing arguments into different primitive
 * types and parsing flags
 *
 * @author Ollie
 */
public class Arguments {
    /**
     * A List of all of the arguments in ChatSection form
     */
    private final List<ChatSection> all;
    /**
     * A List of arguments, not including flag arguments, in ChatSection form
     */
    private final List<ChatSection> arguments;
    /**
     * A List of all flags prefixed with -
     */
    private final List<Flag> flags;
    /**
     * A List of all flags prefixed with --
     */
    private final List<Flag> doubleFlags;
    /**
     * The raw String[] of arguments for this Arguments object
     */
    private final String[] raw;

    /**
     * The Params object for this Arguments object. This contains a Map of
     * parameter names to ParamChatSection values for each registered parameter
     * for the command
     */
    private Params parameters;

    /**
     * Creates a new Arguments object and immediately parses the given String[]
     * of arguments into ChatSections and Flags.
     *
     * @param parse The String[] of raw arguments to parse
     */
    public Arguments(final String... parse) {
        this.all = new ArrayList<>();
        this.arguments = new ArrayList<>();
        this.flags = new ArrayList<>();
        this.doubleFlags = new ArrayList<>();

        raw = parse;
        for (int i = 0; i < raw.length; i++) {
            final String arg = raw[i];
            all.add(new ChatSection(arg));

            switch (arg.charAt(0)) {
                case '-':
                    if (arg.length() < 2) {
                        arguments.add(new ChatSection(arg));
                        continue;
                    }
                    if (arg.charAt(1) == '-') {
                        if (arg.length() < 3) {
                            arguments.add(new ChatSection(arg));
                            continue;
                        }
                        // flag with double -- (no value)
                        doubleFlags.add(new Flag(arg.substring(2, arg.length()), null));
                    } else {
                        if (raw.length - 1 == i) {
                            arguments.add(new ChatSection(arg));
                            continue;
                        }
                        // flag with single - (plus value)
                        flags.add(new Flag(arg.substring(1, arg.length()), raw[i + 1]));
                        i++;
                    }
                    break;
                default:
                    // normal argument
                    arguments.add(new ChatSection(raw[i]));
                    break;
            }
        }
    }

    /**
     * Gets the ChatSection for the argument at the given index
     *
     * @param index The index to get the argument from
     * @return A ChatSection object for the argument at the given index
     */
    public ChatSection get(final int index) {
        return getArgument(index, true);
    }

    /**
     * Gets the raw string for the argument at the given index
     *
     * @param index The index to get the argument from
     * @return A raw String for the argument at the given index
     */
    public String getRaw(final int index) {
        return getArgument(index, true).rawString();
    }

    /**
     * Gets the ChatSection for the argument at the given index
     *
     * @param index The index to get the argument from
     * @return A ChatSection object for the argument at the given index
     */
    public ChatSection getArgument(final int index) {
        return getArgument(index, true);
    }

    /**
     * Gets the ChatSection for the argument at the given index
     *
     * @param index           The index to get the argument from
     * @param includeFlagArgs Whether to include flag arguments
     * @return A ChatSection object for the argument at the given index
     */
    public ChatSection getArgument(final int index,
            final boolean includeFlagArgs) {
        if (includeFlagArgs) {
            return all.get(index);
        } else {
            return arguments.get(index);
        }
    }

    /**
     * Gets the Params for this set of Arguments
     *
     * @return This Arguments object's Params object
     */
    public Params getParams() {
        return parameters;
    }

    /**
     * Checks whether Params are available for these Arguments
     *
     * @return True if this Arguments object has a Params object, otherwise false
     */
    public boolean hasParams() {
        return getParams() != null;
    }

    /**
     * Gets a ParamChatSection value for the parameter with the given name, if
     * there is a Params object available for these Arguments and said Params
     * object contains a value for the given parameter. If either of these
     * conditions are not true, null is returned
     *
     * @param parameter The parameter to get the ParamChatSection value for
     * @return A ParamChatSection for the given parameter, or null if there isn't
     * one
     */
    public Parameter getParam(String parameter) {
        if (!hasParams()) {
            return null;
        }
        return getParams().get(parameter);
    }

    /**
     * Checks whether the given parameter is available in this Arguments' Params
     * object
     *
     * @param parameter The parameter to check for the presence of
     * @return Whether the given parameter is available
     */
    public boolean hasParam(String parameter) {
        return hasParams() && getParams().has(parameter);
    }

    public Flag getValueFlag(final String flag) {
        for (final Flag f : flags) {
            if (f.getName().equalsIgnoreCase(flag)) {
                return f;
            }
        }
        return null;
    }

    public boolean hasValueFlag(final String flag) {
        for (final Flag f : flags) {
            if (f.getName().equalsIgnoreCase(flag)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasNonValueFlag(final String flag) {
        for (final Flag f : doubleFlags) {
            if (f.getName().equalsIgnoreCase(flag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the length of the arguments
     *
     * @return The amount of arguments in this Arguments object
     */
    public int length() {
        return all.size();
    }

    /**
     * Converts this Arguments object to a raw String[] of arguments
     *
     * @return A raw String[] of arguments for this object
     */
    public String[] toStringArray() {
        String[] result = new String[raw.length];
        System.arraycopy(raw, 0, result, 0, raw.length);
        return result;
    }

    /**
     * Sets the Params object for this Arguments object. Should only be called
     * directly after creation. If this is called multiple times an
     * IllegalStateException will be thrown
     *
     * @param parameters The Params to set for this Arguments object
     * @return This Arguments object
     */
    public Arguments withParams(final Params parameters) {
        if (this.parameters != null) {
            throw new IllegalStateException();
        }
        this.parameters = parameters;
        return this;
    }
}
