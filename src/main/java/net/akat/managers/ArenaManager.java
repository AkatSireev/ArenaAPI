package net.akat.managers;

import net.akat.arena.Arena;

import java.util.Optional;

public interface ArenaManager {

    /**
     * Проверяет, существует ли папка с ареной.
     * @param arena арена, для которой проверяется наличие папки.
     * @return true, если папка арены существует, иначе false.
     */
    boolean hasArenaFolder(Arena arena);

    /**
     * Проверяет, существует ли папка с ареной по имени папки.
     * @param folder имя папки, которая проверяется.
     * @return true, если папка существует, иначе false.
     */
    boolean hasArenaFolder(String folder);

    /**
     * Сбрасывает арену, удаляя все её данные и загружая заново.
     * @param arena арена, которая будет сброшена.
     */
    void resetArena(Arena arena);

    /**
     * Загружает арену.
     * @param arena арена, которую нужно загрузить.
     */
    void loadArena(Arena arena);

    /**
     * Загружает арену и может сбросить её данные перед загрузкой.
     * @param arena арена, которую нужно загрузить.
     * @param reset если true, то перед загрузкой арена будет сброшена.
     */
    void loadArena(Arena arena, boolean reset);

    /**
     * Проверяет, была ли арена уже создана.
     * @param arena имя арены, которое нужно проверить.
     * @return true, если арена была создана, иначе false.
     */
    boolean isArenaCreated(String arena);

    /**
     * Получает арены по имени.
     * @param arena имя арены, которую нужно получить.
     * @return Optional, содержащий арену, если она существует.
     */
    Optional<Arena> getArena(String arena);

    /**
     * Очищает все арены.
     */
    void clearAll();

    /**
     * Очищает конкретную арену.
     * @param arena арена, которую нужно очистить.
     */
    void clear(Arena arena);

    /**
     * Удаляет файлы, которые не были удалены из-за ошибки или сбоя.
     */
    void removerStartup();
}
