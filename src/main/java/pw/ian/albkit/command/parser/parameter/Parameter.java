package pw.ian.albkit.command.parser.parameter;

/**
 * Represents a parameter, which can be required or optional
 *
 * @author Ollie
 */
public class Parameter {
    /**
     * The name of this parameter
     */
    private final String name;
    /**
     * Whether this parameter is optional
     */
    private final boolean optional;

    public Parameter(String name, boolean optional) {
        this.name = name;
        this.optional = optional;
    }

    /**
     * Gets the name of this parameter
     *
     * @return The name of this parameter
     */
    public String getName() {
        return name;
    }

    /**
     * Returns whether this parameter is optional
     *
     * @return True if the parameter is optional, false if it is required
     */
    public boolean isOptional() {
        return optional;
    }
}
