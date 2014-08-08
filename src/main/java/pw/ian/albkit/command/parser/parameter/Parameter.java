package pw.ian.albkit.command.parser.parameter;

/**
 * Represents a parameter, which can be required or optional
 *
 * @author Ollie
 */
public class Parameter {
    private final String name;
    private final boolean optional;

    public Parameter(String name, boolean optional) {
        this.name = name;
        this.optional = optional;
    }

    public String getName() {
        return name;
    }

    public boolean isOptional() {
        return optional;
    }
}
