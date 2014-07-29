/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pw.ian.albkit.command;

import pw.ian.albkit.command.parser.Arguments;

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

    public CommandHandler(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        usage = "/" + name;
        description = usage;
        permission = null;
        async = false;
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

    /**
     * @param usage the usage to set
     */
    public void setUsage(String usage) {
        this.usage = usage;
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
        if (async) {
            executeAsync(sender, args);
        } else {
            this.onCommand(sender, new Arguments(args));
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
                CommandHandler.this.onCommand(sender, new Arguments(args));
                CommandHandler.this.onCommand(sender, args);
            }
        }).runTaskAsynchronously(plugin);
    }

    /**
     * Command handler method. Override this if you want to use a synchronous
     * command.
     *
     * @param sender
     * @param args
     */
    public void onCommand(final CommandSender sender, final Arguments args) {
    }

    /**
     * Command handler method. Override this if you want to use a synchronous
     * command.
     *
     * @param sender
     * @param args
     */
    public void onCommand(final CommandSender sender, final String[] args) {
    }
}
