package net.akat.arena;

import org.bukkit.World;

import java.io.File;
import java.util.ArrayList;

public class Arena {

    public static ArrayList<Arena> ARENA_LIST = new ArrayList<>();

    private String name;
    private World world;
    private String sourceWorldFolder;
    private File activeWorldFolder;

    public Arena(String name, String sourceWorldFolder) {
        this.name = name;
        this.sourceWorldFolder = sourceWorldFolder;
    }

    // Геттеры
    public String getName() {
        return name;
    }

    public World getWorld() {
        return world;
    }

    public String getSourceWorldFolder() {
        return sourceWorldFolder;
    }

    public File getActiveWorldFolder() {
        return activeWorldFolder;
    }

    // Сеттеры
    public void setName(String name) {
        this.name = name;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setSourceWorldFolder(String sourceWorldFolder) {
        this.sourceWorldFolder = sourceWorldFolder;
    }

    public void setActiveWorldFolder(File activeWorldFolder) {
        this.activeWorldFolder = activeWorldFolder;
    }
}
