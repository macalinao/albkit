package pw.ian.albkit.command.parser;

/**
 * A flag which simply has a name and a value
 *
 * @author Ollie
 */
public class Flag {
    /**
     * The name of this flag. For example, if the user entered '-lol tree', this
     * would be 'lol'
     */
    private final String flag;
    /**
     * The raw value of this flag. For example, if the user entered '-lol tree'.
     * this would be 'tree'
     */
    private final String value;
    /**
     * The ChatSection representing the value of this flag, which provides
     * various methods to use the value
     */
    private final ChatSection valArg;

    public Flag(final String flag, final String value) {
        this.flag = flag;
        this.value = value;

        valArg = new ChatSection(value);
    }

    public String getName() {
        return flag;
    }

    public ChatSection getValue() {
        return valArg;
    }

    public String getRawValue() {
        return value;
    }
}
