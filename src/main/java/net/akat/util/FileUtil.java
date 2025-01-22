package net.akat.util;

import java.io.*;

public final class FileUtil {

    private static final int BUFFER_SIZE = 1024;

    public static void copy(File source, File destination) throws IOException {
        if (source.isDirectory()) {
            copyDirectory(source, destination);
        } else {
            copyFile(source, destination);
        }
    }

    private static void copyDirectory(File source, File destination) throws IOException {
        if (!destination.exists() && !destination.mkdir()) {
            throw new IOException("Не удалось создать целевую директорию: " + destination);
        }

        String[] files = source.list();
        if (files == null) {
            return;
        }

        for (String file : files) {
            File newSource = new File(source, file);
            File newDestination = new File(destination, file);
            copy(newSource, newDestination);
        }
    }

    private static void copyFile(File source, File destination) throws IOException {
        try (InputStream in = new FileInputStream(source);
             OutputStream out = new FileOutputStream(destination)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }

    public static void delete(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File child : files) {
                    delete(child);
                }
            }
        }
        if (!file.delete()) {
            System.err.println("Не удалось удалить: " + file);
        }
    }
}
