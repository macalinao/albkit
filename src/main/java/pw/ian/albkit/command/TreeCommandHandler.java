/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pw.ian.albkit.command;

import pw.ian.albkit.command.parser.Arguments;
import pw.ian.albkit.command.parser.parameter.ParamsBase;
import pw.ian.albkit.util.ColorScheme;
import pw.ian.albkit.util.Messaging;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * @author ian
 */
public abstract class TreeCommandHandler extends CommandHandler {

    private final Map<String, CommandData> subcommands = new HashMap<>();

    private ColorScheme colorScheme = ColorScheme.DEFAULT;

    public TreeCommandHandler(String name) {
        super(name);
    }

    public TreeCommandHandler(JavaPlugin plugin, String name) {
        super(plugin, name);
    }

    public void setColorScheme(ColorScheme colorScheme) {
        this.colorScheme = colorScheme;
    }

    /**
     * Sets up the subcommands in this command handler
     */
    public abstract void setupSubcommands();

    /**
     * Called when the command has no arguments.
     *
     * @param sender
     */
    public void onCommandNoArgs(CommandSender sender) {
        sendHelpMenu(sender);
    }

    /**
     * Called when the command has an invalid subcommand name.
     *
     * @param sender
     */
    public void onCommandInvalidArgs(CommandSender sender) {
        sendHelpMenu(sender);
    }

    /**
     * Sends the given player a help menu about the command.
     *
     * @param sender
     */
    public void sendHelpMenu(CommandSender sender) {
        List<CommandData> cmds = new ArrayList<>(subcommands.values());
        Collections.sort(cmds, new Comparator<CommandData>() {
            @Override
            public int compare(CommandData t, CommandData t1) {
                return t.getName().compareToIgnoreCase(t1.getName());
            }
        });

        List<String> msgs = new ArrayList<>();

        for (CommandData data : cmds) {
            CommandHandler handler = data.getHandler();
            if (handler.getPermission() == null
                    || sender.hasPermission(handler.getPermission())) {
                msgs.add(ChatColor.GREEN + "/" + getName() + " " + handler
                        .getName() + " - "
                        + ChatColor.YELLOW + handler.getDescription());
            }
        }

        Messaging.sendBanner(colorScheme, sender, msgs.toArray());
    }

    /**
     * Adds a subcommand to this tree command handler
     *
     * @param handler
     */
    protected void addSubcommand(CommandHandler handler) {
        addSubcommand(handler.getName(), handler);
    }

    /**
     * Adds a subcommand to this tree command handler
     *
     * @param name
     * @param handler
     */
    protected void addSubcommand(String name, CommandHandler handler) {
        CommandData data = new CommandData(name.toLowerCase(), handler,
                ParamsBase.fromUsageString(
                        handler.getUsage()));
        subcommands.put(data.getName(), data);
    }

    @Override
    public void onCommand(CommandSender sender, Arguments args) {
        if (args.length() == 0) {
            onCommandNoArgs(sender);
            return;
        }

        CommandData data = subcommands.get(args.getRaw(0));
        CommandHandler handler = data.getHandler();
        if (handler != null) {
            Arguments newArgs = new Arguments(
                    Arrays.copyOfRange(args.toStringArray(), 1, args.length()));
            handler.onCommand(sender, newArgs.withParams(
                    data.getParamsBase().createParams(newArgs)));
            return;
        }

        onCommandInvalidArgs(sender);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            onCommandNoArgs(sender);
            return;
        }

        CommandHandler handler = subcommands.get(args[0]).getHandler();
        if (handler != null) {
            handler.onCommand(sender, Arrays.copyOfRange(args, 1, args.length));
            return;
        }

        onCommandInvalidArgs(sender);
    }

}
