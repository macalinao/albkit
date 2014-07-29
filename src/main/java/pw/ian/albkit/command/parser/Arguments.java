package pw.ian.albkit.command.parser;

import java.util.ArrayList;
import java.util.List;

public class Arguments {
    private final List<ChatSection> all;
    private final List<ChatSection> arguments;
    private final List<Flag> flags;
    private final List<Flag> doubleFlags;

    public Arguments(final String[] parse) {
        this.all = new ArrayList<>();
        this.arguments = new ArrayList<>();
        this.flags = new ArrayList<>();
        this.doubleFlags = new ArrayList<>();

        parseArguments(parse);
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
                        throw new IllegalArgumentException(
                                "Invalid argument supplied: " + arg);
                    }
                    if (arg.charAt(1) == '-') {
                        if (arg.length() < 3) {
                            throw new IllegalArgumentException(
                                    "Invalid argument supplied: " + arg);
                        }
                        // flag with double -- (no value)
                        doubleFlags.add(new Flag(arg.substring(2, arg.length()),
                                null));
                    } else {
                        if (args.length - 1 == i) {
                            throw new IllegalArgumentException(
                                    "Expected value for flag: " + arg);
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
}
