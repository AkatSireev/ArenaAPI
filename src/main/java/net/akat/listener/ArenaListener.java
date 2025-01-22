package net.akat.listener;

import net.akat.ArenaAPI;
import net.akat.events.ArenaCreateEvent;
import net.akat.events.ArenaRemoveEvent;
import net.akat.events.ArenaResetEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ArenaListener implements Listener {

    @EventHandler
    public void event(ArenaCreateEvent e) {
        ArenaAPI.getInstance().getServer().broadcastMessage(ChatColor.GREEN + "Арена успешно создана: " + e.getArena().getName());
    }

    @EventHandler
    public void event(ArenaRemoveEvent e) {
        ArenaAPI.getInstance().getServer().broadcastMessage(ChatColor.GREEN + "Арена успешно удалена: " + e.getArena());
    }

    @EventHandler
    public void event(ArenaResetEvent e) {
        ArenaAPI.getInstance().getServer().broadcastMessage(ChatColor.GREEN + "Арена успешно сброшена: " + e.getArena().getName());
    }

}
