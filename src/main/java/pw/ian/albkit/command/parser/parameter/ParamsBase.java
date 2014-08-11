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
     * Creates a new ParamsBase for the given List of Parameters and the given
     * amount of arguments before the first parameter
     *
     * @param params           The parameters for this ParamsBase
     * @param argsBeforeParams The amount of arguments before the first param
     */
    private ParamsBase(List<ParamInfo> params, int argsBeforeParams) {
        this.params = params;
        this.argsBeforeParams = argsBeforeParams;
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
        while (true) {
            if (curArgument >= args.length() || curParam >= params.size()) {
                break;
            }

            String val = args.getRaw(curArgument);
            ParamInfo info = params.get(curParam);
            paramsMap.put(info.getName(), new Parameter(val, info));
            curArgument++;
            curParam++;
        }
        int amtRequired = 0;
        for (ParamInfo info : this.params) {
            if (!info.isOptional()) {
                amtRequired++;
            }
        }
        Params params = new Params(this, paramsMap);
        if (amtRequired > args.length() - argsBeforeParams) {
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

        for (char ch : usageString.toCharArray()) {
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
                builder = null;
                continue;
            } else if (optional && ch == OPTIONAL_CLOSE_DENOTATION) {
                optional = false;
                res.add(new ParamInfo(builder.toString(), true));
                builder = null;
                continue;
            }

            if (required || optional) {
                if (ch == ARGUMENT_SEPARATOR) {
                    // Workaround for flag arguments. I.E the usage submitted
                    // for '-f flag' should be "<-f flag>" and this will split
                    // the two into two separate arguments. This is the best way
                    // to avoid inaccuracies as this system doesn't support
                    // having non-parameter arguments after the first parameter
                    res.add(new ParamInfo(builder.toString(), optional));
                    builder = new StringBuilder();
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
        return new ParamsBase(res, before);
    }
}
