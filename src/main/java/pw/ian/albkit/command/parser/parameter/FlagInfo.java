package pw.ian.albkit.command.parser.parameter;

/**
 * Basic flag information
 */
public class FlagInfo {
    /**
     * Name of the flag
     */
    private final String name;
    /**
     * One-word description
     */
    private final String desc;
    private final boolean optional;

    public FlagInfo(String name, String desc, boolean optional) {
        this.name = name;
        this.desc = desc;
        this.optional = optional;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isOptional() {
        return optional;
    }
}
