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
    private final Map<String, Parameter> params;
    /**
     * Base information for these Params
     */
    private final ParamsBase base;

    /**
     * Whether this set of parameters is valid
     */
    private boolean valid = true;

    /**
     * Creates a new set of Params from the given Map of parameters to values
     *
     * @param base   Base information for these params
     * @param params The parameters and their values for this Params object
     */
    public Params(ParamsBase base, Map<String, Parameter> params) {
        this.params = params;
        this.base = base;
    }

    /**
     * Gets a ParamChatSection value for the given parameter
     *
     * @param parameter The parameter to get the value for
     * @return A ParamChatSection for the given parameter
     */
    public Parameter get(String parameter) {
        return params.get(parameter);
    }

    /**
     * Gets the ParamsBase object for this set of Params. Contains basic info
     * about the parameters involved
     *
     * @return Basic information about these Params
     */
    public ParamsBase getBaseInfo() {
        return base;
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
    public Set<Parameter> values() {
        return new HashSet<>(params.values());
    }

    /**
     * Gets a Set of all of the entries to the Map of parameters contained by
     * this Params object
     *
     * @return A Set of all entries to this Params' Map
     */
    public Set<Entry<String, Parameter>> entries() {
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
