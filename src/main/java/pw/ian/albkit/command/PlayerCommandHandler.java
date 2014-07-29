/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pw.ian.albkit.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author ian
 */
public abstract class PlayerCommandHandler extends CommandHandler {

    public PlayerCommandHandler(JavaPlugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
            return;
        }

        onCommand((Player) sender, args);
    }

    public abstract void onCommand(Player player, String[] args);

}
