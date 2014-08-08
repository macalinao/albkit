package pw.ian.albkit.command.parser.parameter;

import java.util.Map;

/**
 * A wrapper around a HashMap to provide a set of parameters for commands
 *
 * @author Ollie
 */
public class Params {
    private final Map<String, ParamChatSection> params;

    public Params(Map<String, ParamChatSection> params) {
        this.params = params;
    }

    public ParamChatSection get(String parameter) {
        return params.get(parameter);
    }

    public boolean has(String parameter) {
        return params.containsKey(parameter);
    }
}
