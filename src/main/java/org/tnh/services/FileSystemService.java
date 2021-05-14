package org.tnh.services;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemService {
    public static String APPLICATION_FOLDER = ".cooking-recipes";
    private static final String USER_FOLDER = System.getProperty("user.home");

    public static Path getPathToFile(String... path) {
        return getApplicationHomeFolder().resolve(Paths.get(".", path));
    }

    @NotNull
    public static Path getApplicationHomeFolder() {
        return Paths.get(USER_FOLDER, APPLICATION_FOLDER);
    }

    public static void initDirectory() {
        Path applicationHomePath = getApplicationHomeFolder();
        if (!Files.exists(applicationHomePath)) {
            boolean wasSuccessful;
            wasSuccessful = applicationHomePath.toFile().mkdirs();
            if (!wasSuccessful) {
                System.out.println("was not successful.");
            }
        }
    }

}
