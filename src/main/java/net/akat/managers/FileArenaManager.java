package net.akat.managers;

import net.akat.ArenaAPI;
import net.akat.arena.Arena;
import net.akat.events.ArenaCreateEvent;
import net.akat.events.ArenaRemoveEvent;
import net.akat.events.ArenaResetEvent;
import net.akat.util.FileUtil;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class FileArenaManager implements ArenaManager {

    public static ArrayList<String> ARENA_LIST = new ArrayList<>();

    protected File mapsFolder;

    public FileArenaManager() {
        mapsFolder = ArenaAPI.getInstance().getMapsFolder();
        if (!mapsFolder.exists()) {
            mapsFolder.mkdir();
        }
    }

    @Override
    public boolean hasArenaFolder(Arena arena) {
        return Arrays.stream(mapsFolder.listFiles()).anyMatch(folder -> folder.getName().equals(arena.getSourceWorldFolder()));
    }

    @Override
    public boolean hasArenaFolder(String folder) {
        return Arrays.stream(mapsFolder.listFiles()).anyMatch(f -> f.getName().equals(folder));
    }

    @Override
    public void resetArena(Arena arena) {

        ARENA_LIST.remove(arena.getWorld().getName());

        if (arena.getWorld() != null) ArenaAPI.getInstance().getServer().unloadWorld(arena.getWorld(), false);
        if (arena.getActiveWorldFolder() != null) FileUtil.delete(arena.getActiveWorldFolder());

        arena.setWorld(null);
        arena.setActiveWorldFolder(null);

        ArenaAPI.getInstance().getLogger().info("Арена сброшена!");

        ArenaAPI.getInstance().getServer().getPluginManager().callEvent(new ArenaResetEvent(arena));

        loadArena(arena, true);

    }

    @Override
    public void loadArena(Arena arena) {
        if (arena.getWorld() != null) return;

        String key = "NEFT" + arena.getName() + "_" + System.currentTimeMillis();
        File file = new File(ArenaAPI.getInstance().getServer().getWorldContainer(), key);
        file.mkdir();

        if (getSourceFolder(arena.getSourceWorldFolder()) != null) {
            File source = getSourceFolder(arena.getSourceWorldFolder());
            try {
                FileUtil.copy(source, file);
            } catch (Exception e) {
                ArenaAPI.getInstance().getLogger().info("Произошла ошибка при копировании исходной карты.");
                e.printStackTrace();
            }

            ArenaAPI.getInstance().getServer().getScheduler().runTaskLater(ArenaAPI.getInstance(), () -> {
                World w = ArenaAPI.getInstance().getServer().createWorld(new WorldCreator(key));

                arena.setWorld(w);
                arena.setActiveWorldFolder(file);

                ARENA_LIST.add(key);
                Arena.ARENA_LIST.add(arena);

                ArenaAPI.getInstance().getLogger().info("Арена создана!");

                ArenaAPI.getInstance().getServer().getPluginManager().callEvent(new ArenaCreateEvent(arena));
            }, 40);

        } else {
            ArenaAPI.getInstance().getLogger().info("Исходная карта не найдена.");
        }
    }

    @Override
    public void loadArena(Arena arena, boolean reset) {
        if (arena.getWorld() != null) return;

        String key = "NEFT" + arena.getName() + "_" + System.currentTimeMillis();
        File file = new File(ArenaAPI.getInstance().getServer().getWorldContainer(), key);
        file.mkdir();

        if (getSourceFolder(arena.getSourceWorldFolder()) != null) {
            File source = getSourceFolder(arena.getSourceWorldFolder());
            try {
                FileUtil.copy(source, file);
            } catch (Exception e) {
                ArenaAPI.getInstance().getLogger().info("Произошла ошибка при копировании исходной карты.");
                e.printStackTrace();
            }

            ArenaAPI.getInstance().getServer().getScheduler().runTaskLater(ArenaAPI.getInstance(), () -> {
                World w = ArenaAPI.getInstance().getServer().createWorld(new WorldCreator(key));

                arena.setWorld(w);
                arena.setActiveWorldFolder(file);

                ARENA_LIST.add(key);

                Arena.ARENA_LIST.add(arena);

                ArenaAPI.getInstance().getLogger().info("Арена создана!");
            }, 40);
        } else {
            ArenaAPI.getInstance().getLogger().info("Исходная карта не найдена.");
        }
    }

    @Override
    public boolean isArenaCreated(String arena) {
        return Arena.ARENA_LIST.stream().anyMatch(a -> a.getName().equals(arena));
    }

    @Override
    public Optional<Arena> getArena(String arena) {
        for (Arena a : Arena.ARENA_LIST) {
            if (a.getName().equals(arena)) {
                return Optional.of(a);
            }
        }
        return Optional.empty();
    }

    @Override
    public void clearAll() {
        Arena.ARENA_LIST.stream().forEach(a -> {
            clear(a);
        });
    }

    @Override
    public void clear(Arena arena) {

        System.out.println(arena);
        if (arena == null) {
            return;
        }

        ArenaAPI.getInstance().getServer().getPluginManager().callEvent(new ArenaRemoveEvent(arena.getName()));
        resetArena(arena);
        Arena.ARENA_LIST.remove(arena);

    }

    @Override
    public void removerStartup() {
        Arrays.stream(ArenaAPI.getInstance().getServer().getWorldContainer().listFiles()).forEach(file -> {
            if (file.isDirectory() && file.canWrite() && file.getName().startsWith("NEFT")) {
                FileUtil.delete(file);
            }
        });
    }

    private File getSourceFolder(String folder) throws NullPointerException {
        for (File f : mapsFolder.listFiles()) {
            if (f.getName().equals(folder)) {
                return f;
            }
        }

        return null;
    }

}
