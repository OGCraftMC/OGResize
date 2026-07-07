package org.ogcraft.ogresize;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.ogcraft.ogresize.command.ResizeCommand;
import org.ogcraft.ogresize.gui.ResizeListener;
import org.ogcraft.ogresize.util.JoinListener;
import org.ogcraft.ogresize.util.ResizePersistence;
import org.ogcraft.ogresize.util.ScaleUtil;
import org.bstats.bukkit.Metrics;

import java.io.InputStream;
import java.io.InputStreamReader;

public final class OGResize extends JavaPlugin {

    // Static instance for global access
    private static OGResize instance;

    // Plugin is enabled by default
    private boolean resizeEnabled = true;

    // Error handling for access before initialization
    public static OGResize getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Plugin not initialized yet");
        }
        return instance;
    }

    // Returns plugin on/off state
    public boolean isResizeEnabled() {
        return resizeEnabled;
    }

    // Controls plugin on/off state
    public void setResizeEnabled(boolean value) {
        this.resizeEnabled = value;
    }

    // Initializes config, persistence, commands, and listeners
    @Override
    public void onEnable() {

        // Store plugin instance for global access
        instance = this;

        // Register bstats
        int pluginId = 32351;
        new Metrics(this, pluginId);

        // Load default config.yml
        saveDefaultConfig();

        // Update config.yml if necessary
        updateConfig();

        // Load config values into ScaleUtil
        ScaleUtil.loadConfig(this);

        // Create Persistence
        ResizePersistence.init();

        // Register Commands
        registerCommands();

        // Register Listeners
        registerListeners();
    }

    // Called when plugin is disabled
    @Override
    public void onDisable() {
        //disables plugin
        getLogger().info("OGResize disabled!");
    }

    // Config Reload
    public void reloadPlugin() {
        reloadConfig();
        updateConfig();
        ScaleUtil.loadConfig(this);
    }

    // Command Registration
    private void registerCommands() {
        var command = getCommand("resize");

        // Handles plugin.yml misconfiguration error
        if (command != null) {
            command.setExecutor(new ResizeCommand());
        } else {
            getLogger().severe("Command 'resize' not found in plugin.yml!");
        }
    }

    // Listener Registration
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ResizeListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }

    // Update config.yml
    private void updateConfig() {

        // Grab current server config
        FileConfiguration current = getConfig();

        // Get default config stored in the jar
        InputStream defaultStream = getResource("config.yml");

        // Safety check
        if (defaultStream == null) {
            getLogger().warning("Could not load default config.yml for update check.");
            return;
        }

        // Load default Config
        YamlConfiguration defaults = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
        boolean changed = false;

        // Check every value for a change
        for (String key : defaults.getKeys(true)) {

            if (!current.contains(key)) {
                current.set(key, defaults.get(key));
                changed = true;
            }
        }

        // Safe config only if changes were made
        if (changed) {
            saveConfig();
            getLogger().info("Config updated.");
        }
    }

    // Message Helpers

    public String get(String path) {
        return OGResize.getInstance().getConfig().getString(path, "");
    }

    public String msg(String path) {
        String prefix = get("messages.prefix");
        String message = get(path);

        return color(prefix + message);
    }

    public Component msgComponent(String path) {
        return LegacyComponentSerializer.legacySection().deserialize(msg(path));
    }

    public String color(String msg) {
        return msg.replace("&", "§");
    }

    public String format(double value) {
        return String.format("%.1f", value);
    }

}
