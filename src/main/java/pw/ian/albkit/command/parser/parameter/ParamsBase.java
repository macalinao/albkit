package pw.ian.albkit.command.parser.parameter;

import pw.ian.albkit.command.parser.Arguments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A base to create Params objects from - used so that we don't parse the usage
 * string every time a command is executed
 *
 * @author Ollie
 */
public class ParamsBase {
    /**
     * The character which implies the beginning of a required parameter
     */
    public static final char REQUIRED_OPEN_DENOTATION = '<';
    /**
     * The character which implies the closing of a required parameter
     */
    public static final char REQUIRED_CLOSE_DENOTATION = '>';
    /**
     * The character which implies the opening of an optional parameter
     */
    public static final char OPTIONAL_OPEN_DENOTATION = '[';
    /**
     * The character which implies the closing of an optional parameter
     */
    public static final char OPTIONAL_CLOSE_DENOTATION = ']';
    /**
     * The character which separates arguments
     */
    public static final char ARGUMENT_SEPARATOR = ' ';

    /**
     * A list of all of the parameters
     */
    private final List<ParamInfo> params;
    /**
     * The number of arguments before the first parameter
     */
    private final int argsBeforeParams;
    /**
     * The amount of arguments required for a valid Params object for this
     * ParamsBase, used for argument validation
     */
    private final int amtRequired;
    /**
     * Flag information for validation
     */
    private final List<FlagInfo> flags;

    /**
     * Creates a new ParamsBase for the given List of Parameters and the given
     * amount of arguments before the first parameter
     *
     * @param params           The parameters for this ParamsBase
     * @param argsBeforeParams The amount of arguments before the first param
     * @param amtRequired      The amount of required parameters
     */
    private ParamsBase(List<ParamInfo> params, int argsBeforeParams, int amtRequired, List<FlagInfo> flags) {
        this.params = params;
        this.argsBeforeParams = argsBeforeParams;
        this.amtRequired = amtRequired;
        this.flags = flags;
    }

    /**
     * Gets the amount of parameters
     *
     * @return The amount of parameters in this ParamsBase
     */
    public int length() {
        return params.size();
    }

    /**
     * Gets the amount of arguments before the first parameter
     *
     * @return The amount of arguments before the first parameter
     */
    public int getArgsBeforeParams() {
        return argsBeforeParams;
    }

    /**
     * Gets the amount of required parameters
     *
     * @return The amount of required parameters
     */
    public int getAmountRequired() {
        return amtRequired;
    }

    /**
     * Gets the amount of non-required (optional) parameters
     *
     * @return The amount of optional parameters
     */
    public int getAmountOptional() {
        return length() - getAmountRequired();
    }

    public int getAmountFlags() {
        return flags.size();
    }

    /**
     * Creates a set of parameters for this base using the given arguments
     *
     * @param args The arguments to create the parameters from
     * @return A set of parameters for the given arguments
     */
    public Params createParams(Arguments args) {
        Map<String, Parameter> paramsMap = new HashMap<>();
        int curArgument = argsBeforeParams;
        int curParam = 0;
        int curFlag = 0;
        boolean invalid = false;

        while (curArgument < args.length(false) && curParam < params.size()) {
            if (curFlag < flags.size()) {
                if (!args.hasValueFlag(flags.get(curFlag++).getName())) {
                    invalid = true;
                }
            }

            String val = args.getRaw(curArgument, false);
            ParamInfo info = params.get(curParam);

            paramsMap.put(info.getName(), new Parameter(val, info));
            curArgument++;
            curParam++;
        }

        while (curFlag < flags.size()) {
            if (!args.hasValueFlag(flags.get(curFlag++).getName())) {
                invalid = true;
            }
        }

        Params params = new Params(this, paramsMap);
        if (invalid || amtRequired > paramsMap.size()) {
            params.invalidate();
        }

        return params;
    }

    /**
     * Builds a new ParamsBase by parsing the given usage string for a command
     *
     * @param usageString The command usage string to parse
     * @return A new ParamsBase created from parsing the given usage string
     */
    public static ParamsBase fromUsageString(String usageString) {
        List<ParamInfo> res = new ArrayList<>();
        boolean required = false;
        boolean optional = false;
        StringBuilder builder = null;
        boolean reachedFirst = false;
        int before = 0;
        boolean passedCommand = false;
        int amtRequired = 0;
        List<FlagInfo> flags = new ArrayList<FlagInfo>();

        final char[] characters = usageString.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            final char ch = characters[i];

            if (!reachedFirst && ch == ARGUMENT_SEPARATOR) {
                if (passedCommand) {
                    before++;
                }
                passedCommand = true;
                continue;
            }

            if (required && ch == REQUIRED_CLOSE_DENOTATION) {
                required = false;
                res.add(new ParamInfo(builder.toString(), false));
                amtRequired++;
                builder = null;
                continue;
            } else if (optional && ch == OPTIONAL_CLOSE_DENOTATION) {
                optional = false;
                res.add(new ParamInfo(builder.toString(), true));
                builder = null;
                continue;
            }

            if (required || optional) {
                final char next = characters[i + 1];
                if (ch == '-' && next != REQUIRED_CLOSE_DENOTATION && next != OPTIONAL_CLOSE_DENOTATION && characters[i + 2] == ' ') {
                    StringBuilder desc = new StringBuilder();
                    int breakPoint = Integer.MAX_VALUE;
                    boolean isOptional = false;
                    for (int j = 3; j < breakPoint; j++) {
                        char toAppend = characters[i + j];
                        if (toAppend == REQUIRED_CLOSE_DENOTATION || toAppend == OPTIONAL_CLOSE_DENOTATION) {
                            if (toAppend == OPTIONAL_CLOSE_DENOTATION) {
                                isOptional = true;
                            }
                            breakPoint = j;
                            continue;
                        }

                        desc.append(toAppend);
                    }
                    flags.add(new FlagInfo(String.valueOf(next), desc.toString(), isOptional));

                    i += 2;
                    required = false;
                    optional = false;
                    builder = null;
                    continue;
                }

                builder.append(ch);
            } else {
                if (ch == REQUIRED_OPEN_DENOTATION) {
                    reachedFirst = true;
                    required = true;
                    builder = new StringBuilder();
                } else if (ch == OPTIONAL_OPEN_DENOTATION) {
                    reachedFirst = true;
                    optional = true;
                    builder = new StringBuilder();
                }
            }
        }

        return new ParamsBase(res, before, amtRequired, flags);
    }
}
