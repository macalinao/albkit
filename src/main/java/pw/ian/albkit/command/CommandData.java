package pw.ian.albkit.command;

import pw.ian.albkit.command.parser.parameter.ParamsBase;

/**
 * Holds data about a command or subcommand
 *
 * @author Ollie
 */
public class CommandData {
    private final String name;
    private final CommandHandler handler;
    private final ParamsBase paramsBase;

    public CommandData(String name, CommandHandler handler,
            ParamsBase paramsBase) {
        this.name = name;
        this.handler = handler;
        this.paramsBase = paramsBase;
    }

    public String getName() {
        return name;
    }

    public CommandHandler getHandler() {
        return handler;
    }

    public ParamsBase getParamsBase() {
        return paramsBase;
    }
}
