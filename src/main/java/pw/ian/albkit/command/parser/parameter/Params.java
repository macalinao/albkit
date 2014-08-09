package pw.ian.albkit.command.parser.parameter;

import java.util.Map;

/**
 * A wrapper around a HashMap to provide a set of parameters for commands
 *
 * @author Ollie
 */
public class Params {
    /**
     * A map of all of the parameters
     */
    private final Map<String, ParamChatSection> params;

    /**
     * Creates a new set of Params from the given Map of parameters to values
     *
     * @param params The parameters and their values for this Params object
     */
    public Params(Map<String, ParamChatSection> params) {
        this.params = params;
    }

    /**
     * Gets a ParamChatSection value for the given parameter
     *
     * @param parameter The parameter to get the value for
     * @return A ParamChatSection for the given parameter
     */
    public ParamChatSection get(String parameter) {
        return params.get(parameter);
    }

    /**
     * Checks whether these Params contain a value for the given parameter
     *
     * @param parameter The parameter to check for the presence of
     * @return Whether the given parameter has a value in this Params object
     */
    public boolean has(String parameter) {
        return params.containsKey(parameter);
    }
}
