package pw.ian.albkit.command.parser.parameter;

import pw.ian.albkit.command.parser.ChatSection;

/**
 * An extension of ChatSection, used for parameters
 *
 * @author Ollie
 */
public class Parameter extends ChatSection {
    /**
     * The information (name, whether it is optional) for this parameter
     */
    private final ParamInfo info;

    /**
     * Creates a new ParamChatSection, using the given String argument as a raw
     * string
     *
     * @param arg  The raw string for this ParamChatSection
     * @param info Information about this parameter
     */
    public Parameter(String arg, ParamInfo info) {
        super(arg);
        this.info = info;
    }

    /**
     * Gets the ParamInfo for this Parameter
     *
     * @return This Parameter's ParamInfo object
     */
    public ParamInfo getInfo() {
        return info;
    }
}
