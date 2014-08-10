/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pw.ian.albkit.util;

import pw.ian.albkit.messaging.FormattedMessage;
import pw.ian.albkit.messaging.MessageBuilder;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * @author ian
 * @author Ollie
 */
public class Messaging {
    public static void sendBanner(CommandSender sender, Object... message) {
        sendBanner(ColorScheme.DEFAULT, sender, message);
    }

    public static void sendBanner(ColorScheme scheme, CommandSender sender, Object... message) {
        sender.sendMessage(ChatColor.GRAY + " " + ChatColor.STRIKETHROUGH + "-" + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "--------------------------------------------------" + ChatColor.GRAY + ChatColor.STRIKETHROUGH + "-");
        for (Object line : message) {
            sender.sendMessage(scheme.getLight() + " " + format(scheme, line.toString()).get());
        }
        sender.sendMessage(ChatColor.GRAY + " " + ChatColor.STRIKETHROUGH + "-" + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "--------------------------------------------------" + ChatColor.GRAY + ChatColor.STRIKETHROUGH + "-");
    }

    public static void formatAndSend(String message, CommandSender sender) {
        format(message).send(sender);
    }

    public static void formatAndSend(ColorScheme scheme, String message, CommandSender sender) {
        format(scheme, message).send(sender);
    }

    public static FormattedMessage format(String message) {
        return format(ColorScheme.DEFAULT, message);
    }

    public static FormattedMessage format(ColorScheme scheme, String message) {
        return createMessage(scheme).append(message).toMessage();
    }

    public static MessageBuilder createMessage() {
        return createMessage(ColorScheme.DEFAULT);
    }

    public static MessageBuilder createMessage(ColorScheme scheme) {
        return new MessageBuilder(scheme);
    }

    private Messaging() {
    }
}
