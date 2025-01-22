package net.akat.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaRemoveEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    private String arena;

    public ArenaRemoveEvent(String arena) {
        this.arena = arena;
    }

    public String getArena() {
        return arena;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
