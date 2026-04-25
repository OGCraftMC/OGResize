package org.ogcraft.ogresize.util;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.ogcraft.ogresize.OGResize;

public class ScaleUtil {

    // Variables
    private static double MIN;
    private static double MAX;
    private static double STEP;
    private static double DEFAULT;

    public static double getMin() {
        return MIN;
    }

    public static  double getMax() {
        return MAX;
    }

    public static double getSTEP() {
        return STEP;
    }

    public static double getDefault() {
        return DEFAULT;
    }

    // Get scale attribute
    private static Attribute getScaleAttribute() {
        return Registry.ATTRIBUTE.get(NamespacedKey.minecraft("scale"));
    }

    // Load and validate values from config.yml
    public static void loadConfig(OGResize plugin) {
        // Pull values from config.yml & includes defaults just in case
        double min = plugin.getConfig().getDouble("settings.min-size", 0.5);
        double max = plugin.getConfig().getDouble("settings.max-size", 1.5);
        double step = plugin.getConfig().getDouble("settings.step-size", 0.1);
        double def = plugin.getConfig().getDouble("settings.default-size", 1.0);

        // Safety check
        if (min <= 0) {
            min = 0.1;
        }
        if (max < min) {
            max = min;
        }
        if (step <= 0) {
            step = 0.1;
        }
        if (def < min || def > max) {
            def = max;
        }

        MIN = min;
        MAX = max;
        STEP = step;
        DEFAULT = def;

    }

    //
    public static double normalize(double size) {
        // Road to nearest step
        size = Math.round(size / STEP) * STEP;

        // Enforce bounds
        if (size < MIN) {
            size = MIN;
        }
        if (size > MAX) {
            size = MAX;
        }

        return size;
    }

    // Main set method
    public static void setScale(Player player, double size) {

        // Valid range
        size = Math.max(MIN, Math.min(MAX, size));

        // Round to nearest increment
        size = Math.round(size / STEP) * STEP;

        // Safe attribute get
        Attribute attribute = getScaleAttribute();
        if (attribute == null) {
            return;
        }

        AttributeInstance instance = player.getAttribute(attribute);
        if (instance == null) {
            return;
        }

        // Apply size
        instance.setBaseValue(size);

        // Save size
        ResizePersistence.save(player.getUniqueId(), size);
    }

    // Get current scale
    public static double getScale(Player player) {
        Attribute attribute = getScaleAttribute();

        if (attribute == null) {
            return DEFAULT;
        }

        AttributeInstance instance = player.getAttribute(attribute);
        if (instance == null) {
            return DEFAULT;
        }

        return instance.getBaseValue();
    }

    // Increase scale
    public static double increase(Player player) {

        // Grab player's current size
        double current = getScale(player);

        // Handle at or above max size
        if (current >= MAX) {
            return current;
        }

        // Calculate next size
        double newSize = current + STEP;

        // Safely ensure max isn't exceeded
        if (newSize > MAX) {
            newSize = MAX;
        }

        // Apply new scale
        setScale(player, newSize);
        return newSize;
    }

    // Decrease scale
    public static double decrease(Player player) {

        // Grab player's current scale
        double current = getScale(player);

        // Handle at or below minimum
        if (current <= MIN) {
            return current;
        }

        // Calculate next size
        double newSize = current - STEP;

        // Safely ensure min size isn't exceeded
        if (newSize < MIN) {
            newSize = MIN;
        }

        // Apply new size
        setScale(player, newSize);
        return newSize;
    }

    // Reset scale
    public static void reset(Player player) {
        setScale(player, DEFAULT);
    }


}
