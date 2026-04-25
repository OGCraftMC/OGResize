package org.ogcraft.ogresize;

import org.bukkit.plugin.java.JavaPlugin;
import org.ogcraft.ogresize.command.ResizeCommand;
import org.ogcraft.ogresize.gui.ResizeListener;
import org.ogcraft.ogresize.util.JoinListener;
import org.ogcraft.ogresize.util.ResizePersistence;
import org.ogcraft.ogresize.util.ScaleUtil;

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

        // Load default config.yml
        saveDefaultConfig();

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
}
