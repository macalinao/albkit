package pw.ian.albkit.command.parser;

import pw.ian.albkit.command.parser.parameter.ParamChatSection;
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

    private Params parameters;

    /**
     * Creates a new Arguments object and immediately parses the given String[]
     * of arguments into ChatSections and Flags.
     *
     * @param parse The String[] of raw arguments to parse
     */
    public Arguments(final String[] parse) {
        this.all = new ArrayList<>();
        this.arguments = new ArrayList<>();
        this.flags = new ArrayList<>();
        this.doubleFlags = new ArrayList<>();

        parseArguments(parse);
    }

    public ChatSection get(final int index) {
        return getArgument(index, true);
    }

    public String getRaw(final int index) {
        return getArgument(index, true).rawString();
    }

    public ChatSection getArgument(final int index) {
        return getArgument(index, true);
    }

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
    public ParamChatSection getParam(String parameter) {
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

    public int length() {
        return all.size();
    }

    public String[] toStringArray() {
        final String[] res = new String[all.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = all.get(i).rawString();
        }
        return res;
    }

    private void parseArguments(final String[] args) {
        for (int i = 0; i < args.length; i++) {
            final String arg = args[i];
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
                        doubleFlags.add(new Flag(arg.substring(2,
                                arg.length()), null));
                    } else {
                        if (args.length - 1 == i) {
                            arguments.add(new ChatSection(arg));
                            continue;
                        }
                        // flag with single - (plus value)
                        flags.add(new Flag(arg.substring(1, arg.length()),
                                args[i + 1]));
                        i++;
                    }
                    break;
                default:
                    // normal argument
                    arguments.add(new ChatSection(args[i]));
                    break;
            }
        }
    }

    public Arguments withParams(Params parameters) {
        if (this.parameters != null) {
            throw new IllegalStateException();
        }
        this.parameters = parameters;
        return this;
    }
}
