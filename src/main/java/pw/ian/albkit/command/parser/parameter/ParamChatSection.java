package pw.ian.albkit.command.parser.parameter;

import pw.ian.albkit.command.parser.ChatSection;

/**
 * An extension of ChatSection, used for parameters
 *
 * @author Ollie
 */
public class ParamChatSection extends ChatSection {
    /**
     * Whether the parameter is optional
     */
    private final boolean optional;

    /**
     * Creates a new ParamChatSection, using the given String argument as a raw
     * string
     *
     * @param arg      The raw string for this ParamChatSection
     * @param optional Whether this parameter is optional
     */
    public ParamChatSection(String arg, boolean optional) {
        super(arg);
        this.optional = optional;
    }

    /**
     * Returns whether the parameter is optional
     *
     * @return True if the parameter is optional, false if it is required
     */
    public boolean isOptional() {
        return optional;
    }
}
