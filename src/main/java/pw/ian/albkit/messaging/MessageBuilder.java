package pw.ian.albkit.messaging;

import pw.ian.albkit.util.ColorScheme;

/**
 * Utility class for building formatted messages using semantics as well as MC
 * colour codes
 *
 * @author Ollie
 */
public final class MessageBuilder {
    private ColorScheme scheme;
    private StringBuilder message;

    public MessageBuilder() {
        this(ColorScheme.DEFAULT);
    }

    public MessageBuilder(final ColorScheme scheme) {
        this.scheme = scheme;
    }

    public ColorScheme scheme() {
        return scheme;
    }

    public MessageBuilder scheme(ColorScheme scheme) {
        this.scheme = scheme;
        return this;
    }

    public MessageBuilder highlight() {
        return append(scheme.getHighlight().toString());
    }

    public MessageBuilder light() {
        return append(scheme.getLight().toString());
    }

    public MessageBuilder dark() {
        return append(scheme.getDark().toString());
    }

    public MessageBuilder msg() {
        return append(scheme.getMsg().toString());
    }

    public MessageBuilder prefix() {
        return append(scheme.getPrefix().toString());
    }

    public MessageBuilder append(String string) {
        return doAppend(scheme.format(string));
    }

    public FormattedMessage toMessage() {
        return new FormattedMessage(message.toString());
    }

    private MessageBuilder doAppend(String string) {
        message.append(string);
        return this;
    }
}
