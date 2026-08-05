package org.ogcraft.ogresize.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.ogcraft.ogresize.OGResize;
import org.ogcraft.ogresize.gui.ResizeGUI;
import org.ogcraft.ogresize.util.ScaleUtil;
import org.jetbrains.annotations.NotNull;

public class ResizeCommand implements CommandExecutor{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        // Save Instance
        OGResize plugin = OGResize.getInstance();

        // Check if plugin is enabled
        if (!plugin.isResizeEnabled() && !(args.length > 0 && args[0].equalsIgnoreCase("enable"))) {
            //sender.sendMessage(msg("messages.plugin-disabled"));
            return true;
        }

        // Main Command

        // Player use: /resize
        // Opens GUI
        if (args.length == 0) {

            // GUI is player only
            if (!(sender instanceof Player player)) {
                sender.sendMessage(OGResize.getInstance().msg("messages.player-warning"));
                return true;
            }

            // Permission check
            if (!player.hasPermission("ogresize.self")) {
                player.sendMessage(OGResize.getInstance().msg("messages.no-permission"));
                return true;
            }

            // GUI handling
            ResizeGUI.open(player);
            return true;
        }

        // Staff & Console Use: /resize [username] [size]
        if (args.length == 2) {

            // Permission check
            if (!sender.hasPermission("ogresize.others") && !(sender instanceof ConsoleCommandSender)) {
                sender.sendMessage(OGResize.getInstance().msg("messages.no-permission"));
                return true;
            }

            // Grab player
            Player target = Bukkit.getPlayer(args[0]);

            // Player not found handling
            if (target == null) {
                sender.sendMessage(OGResize.getInstance().msg("messages.player-not-found"));
                return true;
            }

            // Input validation
            double size;
            try {
                size = Double.parseDouble(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage(OGResize.getInstance().msg("messages.invalid-range").replace("%min%", OGResize.getInstance().format(ScaleUtil.getMin())).replace("%max%", OGResize.getInstance().format(ScaleUtil.getMax())));
                return true;
            }

            // Set limits
            double safeSize = ScaleUtil.normalize(size);

            // Apply scale
            ScaleUtil.setScale(target, safeSize);

            // Grabs value to prevent floating point error
            double applied = ScaleUtil.getScale(target);

            // Display confirmation
            sender.sendMessage(OGResize.getInstance().msg("messages.size-confirmation").replace("%player%", target.getName()).replace("%size%", OGResize.getInstance().format(applied)));
            target.sendMessage(OGResize.getInstance().msg("messages.size-override").replace("%size%", OGResize.getInstance().format(applied)));

            return true;
        }

        // Plugin Management Commands

        // Reload Plugin Config
        if (args[0].equalsIgnoreCase("reload")) {

            // Permission check
            if (!sender.hasPermission("ogresize.admin")) {
                sender.sendMessage(OGResize.getInstance().msg("messages.no-permission"));
                return true;
            }
            // Reload Plugin
            plugin.reloadPlugin();

            // Display confirmation
            sender.sendMessage(OGResize.getInstance().msg("messages.plugin-reloaded"));
            return true;
        }

        // Enable Plugin
        if (args[0].equalsIgnoreCase("enable")) {

            // Permission check
            if (!sender.hasPermission("ogresize.admin")) {
                sender.sendMessage(OGResize.getInstance().msg("messages.no-permission"));
                return true;
            }

            // Plugin already enabled handling
            if (plugin.isResizeEnabled()) {
                sender.sendMessage(OGResize.getInstance().msg("messages.already-enabled"));
                return true;
            }

            // Enables plugin
            plugin.setResizeEnabled(true);

            // Displays confirmation
            sender.sendMessage(OGResize.getInstance().msg("messages.plugin-enabled"));

            return true;
        }

        // Disable Plugin
        if (args[0].equalsIgnoreCase("disable")) {

            // Permission check
            if (!sender.hasPermission("ogresize.admin")) {
                sender.sendMessage(OGResize.getInstance().msg("messages.no-permission"));
                return true;
            }

            // Plugin already disabled handling
            if (!plugin.isResizeEnabled()) {
                sender.sendMessage(OGResize.getInstance().msg("messages.already-disabled"));
                return true;
            }

            // Disables plugin
            plugin.setResizeEnabled(false);

            // Displays confirmation
            sender.sendMessage(OGResize.getInstance().msg("messages.plugin-disabled"));

            return true;
        }

        // Help usage: /resize help
        if (args[0].equalsIgnoreCase("help")) {

            // Header
            sender.sendMessage(OGResize.getInstance().color("&d-------- &l&cOG&9Resize&r&f Commands&d --------"));

            // Body
            sender.sendMessage(OGResize.getInstance().color("&9Current Size Range:&f " + OGResize.getInstance().format(ScaleUtil.getMin()) + " - " + OGResize.getInstance().format(ScaleUtil.getMax())));
            sender.sendMessage(OGResize.getInstance().color("&9/resize&f - Open GUI"));
            sender.sendMessage(OGResize.getInstance().color("&9/resize help&f - Displays this page"));
            sender.sendMessage(OGResize.getInstance().color("&9/resize info&f - Displays plugin information"));
            sender.sendMessage(OGResize.getInstance().color("&9/resize [username] [size]&f - Manually change another player's size (&cModeration Only&f)"));

            return true;
        }

        // Information usage: /resize info
        if (args[0].equalsIgnoreCase("info")) {
            String version = plugin.getPluginMeta().getVersion();

            // Header
            sender.sendMessage(OGResize.getInstance().color("&d-------- &l&cOG&9Resize&r&f Information&d --------"));

            // Body
            sender.sendMessage(OGResize.getInstance().color("&9Created by &dOJCream"));
            sender.sendMessage(OGResize.getInstance().color("&9Version:&f " + version));
            sender.sendMessage(OGResize.getInstance().color("&9Usage:&f /resize"));
            sender.sendMessage(OGResize.getInstance().color("&9Documentation:&f https://github.com/OGCraftMC/OGResize"));

            return true;
        }

        // Handle unknown arguments
        sender.sendMessage(OGResize.getInstance().msg("messages.unknown-command"));

        return true;
    }
}
