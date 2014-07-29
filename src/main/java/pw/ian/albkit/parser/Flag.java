package pw.ian.albkit.parser;

public final class Flag {
    private final String flag;
    private final String value;
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
