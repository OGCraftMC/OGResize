package org.ogcraft.ogresize.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.ogcraft.ogresize.OGResize;

import java.io.IOException;
import java.util.UUID;
import java.io.File;

public class ResizePersistence {

    // File reference for playersizes.yml
    private static File file;

    // In-memory YAML config
    private static FileConfiguration config;

    // Initializes persistence system
    public static void init() {
        file = new File(OGResize.getInstance().getDataFolder(), "playersizes.yml");

        // Ensures plugin data folder exists
        // Creates all missing parent directories if needed
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            if (!parent.mkdirs()) {
                throw new IllegalStateException("Failed to create data folder: " + parent.getPath());
            }
        }

        // Creates file if it doesn't exist
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new IllegalStateException("Failed to create playersizes.yml");
                }
            } catch (IOException e) {
                OGResize.getInstance().getLogger().log(java.util.logging.Level.SEVERE, "Could not create playersizes.yml", e);
            }
        }

        // Load YAML file into memory
        config = YamlConfiguration.loadConfiguration(file);
    }

    // Save player's size to UUID
    public static void save(UUID uuid, double scale) {

        // Stores value under: players.<uuid>.scale
        config.set("players." + uuid + ".scale", scale);
        saveFile();
    }

    // Grabs player's saved size via UUID
    // Returns default size (1.0) if there's no value
    public static double getSize(UUID uuid) {
        return config.getDouble("players." + uuid + ".scale", 1.0);
    }

    // Writes in-memory YAML config to playersizes.yml
    private static void saveFile() {
        try {
            config.save(file);
        } catch (IOException e) {
            OGResize.getInstance().getLogger().log(java.util.logging.Level.SEVERE, "Failed to save playersizes.yml", e);
        }
    }
}