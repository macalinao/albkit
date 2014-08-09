package pw.ian.albkit;

import pw.ian.albkit.command.CommandHandler;
import pw.ian.albkit.command.Commands;

import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A base plugin class which makes creating new plugins more convenient, as it
 * removes the necessity to initialise server, plugin manager and services
 * manager variables as well as providing an easy way to register listeners
 *
 * @author Ollie
 */
public abstract class AlbPlugin extends JavaPlugin {

    protected Server server;

    protected PluginManager pluginMgr;

    protected ServicesManager servicesMgr;

    public abstract void onEnable();

    /**
     * Initialises variables etc for this plugin. Should be called at the start
     * of the onEnable() implementation in extensions of this class
     */
    protected void init() {
        server = getServer();
        pluginMgr = server.getPluginManager();
        servicesMgr = server.getServicesManager();
    }

    /**
     * Registers the given listener to this JavaPlugin object
     *
     * @param listener The Listener to register
     */
    protected void register(final Listener listener) {
        pluginMgr.registerEvents(listener, this);
    }

    /**
     * Registers the given CommandHandler to a command with the given name
     *
     * @param name    The name to register the command to
     * @param handler The CommandHandler to register for the command
     */
    protected void register(final String name, final CommandHandler handler) {
        Commands.registerCommand(this, name, handler);
    }

    /**
     * Registers the given CommandHandler
     *
     * @param handler The CommandHandler to register
     */
    protected void register(final CommandHandler handler) {
        register(handler.getName(), handler);
    }

}
