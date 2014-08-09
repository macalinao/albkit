package pw.ian.albkit.command.parser.parameter;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
     * Whether this set of parameters is valid
     */
    private boolean valid = true;

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

    /**
     * Gets a Set of all of the parameter names contained by this Params
     * object's Map of parameters
     *
     * @return A Set of all of the parameter names for this Params
     */
    public Set<String> parameters() {
        return new HashSet<>(params.keySet());
    }

    /**
     * Gets a Set of all of the parameter values contained by this Params
     * object's Map of parameters
     *
     * @return A Set of all of the parameter values for this Params
     */
    public Set<ParamChatSection> values() {
        return new HashSet<>(params.values());
    }

    /**
     * Gets a Set of all of the entries to the Map of parameters contained by
     * this Params object
     *
     * @return A Set of all entries to this Params' Map
     */
    public Set<Entry<String, ParamChatSection>> entries() {
        return new HashSet<>(params.entrySet());
    }

    /**
     * Checks whether this set of parameters is valid for the base it was
     * constructed for. Used for automatic validation of arguments
     *
     * @return Whether this set of parameters is valid
     */
    public boolean valid() {
        return valid;
    }

    /**
     * Invalidates this set of parameters. Should only be used in automatic
     * validation
     */
    void invalidate() {
        valid = false;
    }
}
