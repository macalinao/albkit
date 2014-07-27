/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pw.ian.albkit.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author ian
 */
public abstract class CommandHandler implements CommandExecutor {

    private final String name;

    private String usage;

    private String description;

    private String permission;

    public CommandHandler(String name) {
        this.name = name;
        usage = "/" + name;
        description = usage;
        permission = null;
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

    /**
     * Sends the usage message to the given CommandSender.
     *
     * @param sender
     */
    public void sendUsageMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Usage: " + usage);
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        this.onCommand(cs, args);
        return true;
    }

    public abstract void onCommand(CommandSender sender, String[] args);
}
