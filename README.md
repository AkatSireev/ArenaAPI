
# ArenaAPI

**ArenaAPI** — это библиотека, которая позволяет легко создавать и управлять аренами для мини-игр. Она предоставляет удобные функции для работы с аренами, включая их создание, удаление и сброс.

---

## Команды

### `/arenaapi create <имя арены> <папка с исходным миром>` 
Создаёт новую арену на основе существующего мира. Для этого необходимо, чтобы копия мира находилась в папке `maps` и не содержала файл `uuid.dat`.
### `/arenaapi reset <имя арены>`
Сбрасывает арены в её начальное состояние.
### `/arenaapi remove <имя арены>`
Удаляет арену с сервера.
### `/arenaapi tp <имя арены>`
Телепортирует игрока на арену.
### `/arenaapi list`
Отображает список всех доступных арен.

---

## Подключение через Maven

Добавьте зависимость в ваш `pom.xml`:

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.AkatSireev</groupId>
    <artifactId>ArenaAPI</artifactId>
    <version>8c120c5c</version>
</dependency>
```

---

## Примеры использования

`@EventHandler`:

```java
@EventHandler
public void onArenaCreate(ArenaCreateEvent e) {
    // TODO Событие создания арены
}

@EventHandler
public void onArenaRemove(ArenaRemoveEvent e) {
    // TODO Событие удаления арены
}

@EventHandler
public void onArenaReset(ArenaResetEvent e) {
    // TODO Событие сброса арены
}
```

### Работа с аренами

```java
// Создание новой арены
public void arenaCreate(String arenaName, String sourceName) {
    ArenaAPI api = ArenaAPI.getInstance();
    Arena arena = new Arena(arenaName, sourceName);
    api.getArenaManager().loadArena(arena);
}

// Удаление арены
public void testRemove(String arenaName) {
    ArenaAPI api = ArenaAPI.getInstance();
    Arena arena = api.getArenaManager().getArena(arenaName).get();
    api.getArenaManager().clear(arenaName);
}

// Сброс арены
public void testReset(String arenaName) {
    ArenaAPI api = ArenaAPI.getInstance();
    Arena arena = api.getArenaManager().getArena(arenaName).get();
    api.getArenaManager().resetArena(arenaName);
}
```

---

## API Методы

- **`loadArena(Arena arena)`**  
  Загружает арену, создавая её из исходной карты. Если арена уже существует, метод ничего не делает.

- **`loadArena(Arena arena, boolean reset)`**  
  Загружает арену с возможностью сброса.

- **`resetArena(Arena arena)`**  
  Сбрасывает арену до исходного состояния, удаляя активный мир и перезагружая его.

- **`clear(Arena arena)`**  
  Удаляет арену, освобождая ресурсы и вызывая событие `ArenaRemoveEvent`.

- **`clearAll()`**  
  Удаляет все активные арены.

- **`isArenaCreated(String arena)`**  
  Возвращает `true`, если арена с указанным именем уже создана.

- **`hasArenaFolder(Arena arena)`**  
  Проверяет, существует ли папка для арены.

- **`hasArenaFolder(String folder)`**  
  Проверяет, существует ли папка с заданным именем.

- **`getArena(String arena)`**  
  Возвращает объект `Arena` по имени (в виде `Optional`).

---

