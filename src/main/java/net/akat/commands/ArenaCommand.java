package net.akat.commands;

import net.akat.ArenaAPI;
import net.akat.arena.Arena;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ArenaCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "create":
                    handleCreateCommand(sender, args);
                    break;
                case "reset":
                    handleResetCommand(sender, args);
                    break;
                case "remove":
                    handleRemoveCommand(sender, args);
                    break;
                case "tp":
                    handleTpCommand(sender, args);
                    break;
                case "list":
                    handleListCommand(sender);
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + "Неизвестная команда.");
                    break;
            }
        } else {
            showHelp(sender);
        }
        return true;
    }

    private void handleCreateCommand(CommandSender sender, String[] args) {
        if (args.length > 2) {
            String arena = args[1];
            String source = args[2];

            if (arena != null && source != null) {
                if (!ArenaAPI.getInstance().getArenaManager().hasArenaFolder(source)) {
                    sender.sendMessage(ChatColor.RED + "Исходный файл не найден, поместите ваш мир в папку maps, чтобы добавить исходный файл. (Внимание: Папки миров не должны содержать файл uuid.dat)");
                    return;
                }
                Arena a = new Arena(arena, source);
                ArenaAPI.getInstance().getArenaManager().loadArena(a);
                sender.sendMessage(ChatColor.GREEN + "Арена создана");
            } else {
                sender.sendMessage(ChatColor.RED + "Имя арены и файл ресурса не могут быть пустыми.");
            }
        }
    }

    private void handleResetCommand(CommandSender sender, String[] args) {
        if (args.length > 1) {
            String arena = args[1];
            if (ArenaAPI.getInstance().getArenaManager().isArenaCreated(arena)) {
                Optional<Arena> ar = ArenaAPI.getInstance().getArenaManager().getArena(arena);
                if (!ar.isPresent()) return;

                ArenaAPI.getInstance().getArenaManager().resetArena(ar.get());
                sender.sendMessage(ChatColor.GREEN + "Арена перезагружена");
            } else {
                sender.sendMessage("Не найдено: " + arena);
            }
        }
    }

    private void handleRemoveCommand(CommandSender sender, String[] args) {
        if (args.length > 1) {
            String arena = args[1];
            if (ArenaAPI.getInstance().getArenaManager().isArenaCreated(arena)) {
                Optional<Arena> ar = ArenaAPI.getInstance().getArenaManager().getArena(arena);
                if (!ar.isPresent()) return;

                ArenaAPI.getInstance().getArenaManager().clear(ar.get());
                sender.sendMessage(ChatColor.GREEN + "Арена удалена!");
            } else {
                sender.sendMessage("Не найдено: " + arena);
            }
        }
    }

    private void handleTpCommand(CommandSender sender, String[] args) {
        if (args.length > 1) {
            String arena = args[1];
            if (ArenaAPI.getInstance().getArenaManager().isArenaCreated(arena)) {
                Arena ar = ArenaAPI.getInstance().getArenaManager().getArena(arena).get();
                Player p = (Player) sender;
                p.teleport(new Location(ar.getWorld(), 0, 100, 0));

                sender.sendMessage(ChatColor.GREEN + "Телепортация в арену: " + ar.getName());
            } else {
                sender.sendMessage("Не найдено: " + arena);
            }
        }
    }

    private void handleListCommand(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_GREEN + "Арены: ");
        Arena.ARENA_LIST.forEach(arena -> sender.sendMessage(ChatColor.WHITE + "- " + arena.getName()));
    }

    private void showHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "/arenaapi create <имя арены> <папка с исходным миром>" + ChatColor.RED + "(Внимание: Папки миров не должны содержать uuid.dat)");
        sender.sendMessage(ChatColor.GREEN + "/arenaapi reset <имя арены>");
        sender.sendMessage(ChatColor.GREEN + "/arenaapi tp <имя арены>");
        sender.sendMessage(ChatColor.GREEN + "/arenaapi remove <имя арены>");
        sender.sendMessage(ChatColor.GREEN + "/arenaapi list");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();

        if (args.length > 0 && args.length < 2) {
            list.add("remove");
            list.add("create");
            list.add("tp");
            list.add("reset");
        }

        if (args.length > 1) {
            if (args.length < 3) {
                if (args[0].equals("create") || args[0].equals("reset") || args[0].equals("remove") || args[0].equals("tp")) {
                    Arena.ARENA_LIST.forEach(arena -> list.add(arena.getName()));
                }
            } else {
                if (args.length > 2) {
                    if (args.length < 4) {
                        if (args[0].equals("create")) {
                            Arrays.stream(ArenaAPI.getInstance().getMapsFolder().listFiles()).forEach(f -> list.add(f.getName()));
                        }
                    }
                }
            }
        }
        return list;
    }
}
