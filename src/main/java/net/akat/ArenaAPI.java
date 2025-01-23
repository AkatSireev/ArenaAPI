package net.akat;

import net.akat.listener.PluginListener;
import net.akat.managers.ArenaManager;
import net.akat.managers.FileArenaManager;
import net.akat.commands.ArenaCommand;
import net.akat.listener.ArenaListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class ArenaAPI extends JavaPlugin {

    private static ArenaAPI instance;
    private ArenaManager arenaManager;
    private File mapsFolder;
    private boolean isApiActive = true;

    @Override
    public void onEnable() {
        instance = this;
        initializeConfig();
        initializeMapsFolder();
        initializeArenaManager();
        registerCommandsAndListeners();
    }

    @Override
    public void onDisable() {
        arenaManager.removerStartup();
    }


    // Геттеры
    public static ArenaAPI getInstance() {
        return instance;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public File getMapsFolder() {
        return mapsFolder;
    }

    public boolean isApiActive() {
        return isApiActive;
    }



    // Методы инициализации
    private void initializeConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void initializeMapsFolder() {
        mapsFolder = new File(getServer().getWorldContainer(), "maps");
    }

    private void initializeArenaManager() {
        arenaManager = new FileArenaManager();
        //arenaManager.removerStartup();
    }

    private void registerCommandsAndListeners() {
        getCommand("arenaapi").setExecutor(new ArenaCommand());

        if (getConfig().getBoolean("listeners")) {
            getServer().getPluginManager().registerEvents(new ArenaListener(), this);
        }
        getServer().getPluginManager().registerEvents(new PluginListener(), this);
    }

    public void cleanUpResources() {
        arenaManager.clearAll();
    }
}
