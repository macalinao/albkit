package pw.ian.albkit;

import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AlbPlugin extends JavaPlugin {

    protected Server server;

    protected PluginManager pluginMgr;

    protected ServicesManager servicesMgr;

    public abstract void onEnable();

    protected void init() {
        server = getServer();
        pluginMgr = server.getPluginManager();
        servicesMgr = server.getServicesManager();
    }

    protected void register(final Listener listener) {
        pluginMgr.registerEvents(listener, this);
    }

}
