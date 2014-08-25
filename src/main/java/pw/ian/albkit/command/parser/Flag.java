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
     * The ChatSection representing the value of this flag, which provides
     * various methods to use the value
     */
    private final ChatSection valArg;

    public Flag(final String flag, final String value) {
        this.flag = flag;
        valArg = new ChatSection(value);
    }

    public String getName() {
        return flag;
    }

    public ChatSection getValue() {
        return valArg;
    }

    public String getRawValue() {
        return getValue().rawString();
    }
}
