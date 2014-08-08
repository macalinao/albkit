package pw.ian.albkit.command.parser.parameter;

import pw.ian.albkit.command.parser.ChatSection;

/**
 * An extension of ChatSection, used for parameters
 *
 * @author Ollie
 */
public class ParamChatSection extends ChatSection {
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

    public boolean isOptional() {
        return optional;
    }
}
