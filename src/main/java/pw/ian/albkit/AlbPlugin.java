package pw.ian.albkit;

import pw.ian.albkit.command.CommandHandler;
import pw.ian.albkit.command.Commands;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
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
    /**
     * The Bukkit {@link Server} object
     */
    protected Server server;
    /**
     * The Bukkit {@link PluginManager} object
     */
    protected PluginManager pluginMgr;
    /**
     * The Bukkit {@link ServicesManager} object
     */
    protected ServicesManager servicesMgr;
    /**
     * The Vault {@link Economy} object, or null if Vault isn't present
     */
    private Economy economy;
    /**
     * The Vault {@link Chat} object, or null if Vault isn't present
     */
    private Chat chat;
    /**
     * The Vault {@link Permission} object, or null if Vault isn't present
     */
    private Permission perms;

    /**
     * {@inheritDoc}
     */
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
     * Gets the {@link RegisteredServiceProvider} for the given type
     *
     * @param clazz The class to get the {@link RegisteredServiceProvider} for
     * @param <T>   The type of {@link RegisteredServiceProvider} to be returned
     * @return The {@link RegisteredServiceProvider} for the given type
     */
    protected <T> RegisteredServiceProvider<T> getRegistration(Class<T> clazz) {
        return servicesMgr.getRegistration(clazz);
    }

    /**
     * Gets the providing object for the {@link RegisteredServiceProvider} for
     * the given type
     *
     * @param clazz The class to get the providing object for
     * @param <T>   The type of provider to get
     * @return The registered provider for the given type
     */
    protected <T> T getProvider(Class<T> clazz) {
        RegisteredServiceProvider<T> rsp = getRegistration(clazz);
        if (rsp == null) {
            return null;
        }
        return getRegistration(clazz).getProvider();
    }

    /**
     * Attempts to hook Vault's {@link Economy}. Note that this can fail if
     * Vault isn't present and/or there isn't an economy plugin on the server.
     * If this fails, null is returned
     *
     * @return The hooked {@link Economy} object
     */
    protected Economy hookVaultEcon() {
        return economy = getProvider(Economy.class);
    }

    /**
     * Attempts to hook Vault's {@link Chat}. Note that this can fail if Vault
     * isn't present and/or there isn't a chat plugin on the server. If this
     * fails, null is returned
     *
     * @return The hooked {@link Chat} object
     */
    protected Chat hookVaultChat() {
        return chat = getProvider(Chat.class);
    }

    /**
     * Attempts to hook Vault's {@link Permission}. Note that this can fail if
     * Vault isn't present and/or there isn't a permission plugin on the server.
     * If this fails, null is returned
     *
     * @return The hooked {@link Permission} object
     */
    protected Permission hookVaultPerms() {
        return perms = getProvider(Permission.class);
    }

    /**
     * Gets Vault's {@link Economy} implementation, returning null if Vault's
     * economy hasn't been hooked successfully. To attempt to hook Vault's
     * economy, call {@link #hookVaultEcon()}
     *
     * @return Vault's {@link Economy} object
     */
    protected Economy getVaultEcon() {
        return economy;
    }

    /**
     * Gets Vault's {@link Chat} implementation, returning null if Vault's
     * chat hasn't been hooked successfully. To attempt to hook Vault's chat,
     * call {@link #hookVaultChat()}
     *
     * @return Vault's {@link Chat} object
     */
    protected Chat getVaultChat() {
        return chat;
    }

    /**
     * Gets Vault's {@link Permission} implementation, returning null if Vault's
     * permissions haven't been hooked successfully. To attempt to hook Vault's
     * permissions, call {@link #hookVaultPerms()}
     *
     * @return Vault's {@link Permission} object
     */
    protected Permission getVaultPerms() {
        return perms;
    }

    /**
     * Checks whether Vault economy has been successfully hooked
     *
     * @return {@code true} if Vault's economy have been hooked
     */
    protected boolean vaultEconHooked() {
        return getVaultEcon() != null;
    }

    /**
     * Checks whether Vault chat has been successfully hooked
     *
     * @return {@code true} if Vault's chat has been hooked
     */
    protected boolean vaultChatHooked() {
        return getVaultChat() != null;
    }

    /**
     * Checks whether Vault permissions have been successfully hooked
     *
     * @return {@code true} if Vault's permissions have been hooked
     */
    protected boolean vaultPermsHooked() {
        return getVaultPerms() != null;
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
