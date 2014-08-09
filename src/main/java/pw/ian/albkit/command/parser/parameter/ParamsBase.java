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
     * A list of all of the parameters
     */
    private final List<Parameter> params;
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
    private ParamsBase(List<Parameter> params, int argsBeforeParams) {
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
        Map<String, ParamChatSection> paramsMap = new HashMap<>();
        for (int i = argsBeforeParams - 1; i < args.length(); i++) {
            if (params.size() < i) {
                break;
            }
            Parameter param = params.get(i);
            paramsMap.put(param.getName(),
                    new ParamChatSection(args.getRaw(i), param.isOptional()));
        }
        return new Params(paramsMap);
    }

    /**
     * Builds a new ParamsBase by parsing the given usage string for a command
     *
     * @param usageString The command usage string to parse
     * @return A new ParamsBase created from parsing the given usage string
     */
    public static ParamsBase fromUsageString(String usageString) {
        List<Parameter> res = new ArrayList<>();
        boolean required = false;
        boolean optional = false;
        StringBuilder builder = null;
        boolean reachedFirst = false;
        int before = 0;

        for (char ch : usageString.toCharArray()) {
            if (!reachedFirst && ch == ' ') {
                before++;
                continue;
            }

            if (required && ch == '>') {
                required = false;
                res.add(new Parameter(builder.toString(), false));
                builder = null;
                continue;
            } else if (optional && ch == ']') {
                optional = false;
                res.add(new Parameter(builder.toString(), true));
                builder = null;
                continue;
            }

            if (required || optional) {
                if (ch == ' ') {
                    // Workaround for flag arguments. I.E the usage submitted
                    // for '-f flag' should be "<-f flag>" and this will split
                    // the two into two separate arguments. This is the best way
                    // to avoid inaccuracies as this system doesn't support
                    // having non-parameter arguments after the first parameter
                    res.add(new Parameter(builder.toString(), optional));
                    builder = new StringBuilder();
                    continue;
                }
                builder.append(ch);
            } else {
                if (ch == '<') {
                    reachedFirst = true;
                    required = true;
                    builder = new StringBuilder();
                } else if (ch == '[') {
                    reachedFirst = true;
                    optional = true;
                    builder = new StringBuilder();
                }
            }
        }
        return new ParamsBase(res, before);
    }
}
