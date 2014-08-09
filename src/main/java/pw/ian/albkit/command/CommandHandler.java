/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pw.ian.albkit.command;

import pw.ian.albkit.command.parser.Arguments;
import pw.ian.albkit.command.parser.parameter.ParamsBase;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author ian
 */
public abstract class CommandHandler implements CommandExecutor {

    private final JavaPlugin plugin;

    private final String name;

    private String usage;

    private String description;

    private String permission;

    private boolean async;

    private boolean validateUsage;

    protected ParamsBase paramsBase;

    /**
     * C'tor
     *
     * @param name
     */
    public CommandHandler(String name) {
        this(null, name);
    }

    public CommandHandler(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        usage = "/" + name;
        description = usage;
        permission = null;
        async = false;
        validateUsage = true;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the usage
     */
    public String getUsage() {
        return usage;
    }

    public boolean doesValidateUsage() {
        return validateUsage;
    }

    public ParamsBase getParamsBase() {
        return paramsBase;
    }

    /**
     * @param usage the usage to set
     */
    public void setUsage(String usage) {
        this.usage = usage;
        paramsBase = ParamsBase.fromUsageString(usage);
    }

    public void setValidateUsage(boolean validateUsage) {
        this.validateUsage = validateUsage;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        if (async && plugin == null) {
            throw new IllegalArgumentException("Cannot make command async without a plugin specified in the constructor!");
        }
        this.async = async;
    }

    /**
     * Sends the usage message to the given CommandSender.
     *
     * @param sender
     */
    public void sendUsageMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Usage: " + usage);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmnd, String string, String[] args) {
        if (permission != null && !sender.hasPermission(permission)) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        if (async) {
            executeAsync(sender, args);
        } else {
            this.onCommand(sender, args);
        }
        return true;
    }

    /**
     * Executes this command asynchronously.
     *
     * @param sender
     * @param args
     */
    private void executeAsync(final CommandSender sender, final String[] args) {
        (new BukkitRunnable() {
            @Override
            public void run() {
                CommandHandler.this.onCommand(sender, args);
            }
        }).runTaskAsynchronously(plugin);
    }

    /**
     * Command handler method.
     *
     * @param sender
     * @param args
     */
    public void onCommand(final CommandSender sender, final String[] args) {
        Arguments newArgs = new Arguments(args);
        if (paramsBase != null) {
            newArgs.withParams(paramsBase.createParams(newArgs));
            if (doesValidateUsage() && newArgs.getParams() == null) {
                sender.sendMessage(ChatColor.RED + "Invalid usage, " + getUsage());
                return;
            }
        }
        this.onCommand(sender, newArgs);
    }

    /**
     * Command handler method using the Arguments API.
     *
     * @param sender
     * @param args
     */
    public void onCommand(final CommandSender sender, final Arguments args) {
    }
}
