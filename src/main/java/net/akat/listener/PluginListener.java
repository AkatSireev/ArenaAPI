package net.akat.listener;

import net.akat.ArenaAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

public class PluginListener implements Listener {

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin() == ArenaAPI.getInstance()) {
            //ArenaAPI.getInstance().cleanUpResources();
        }
    }
}
