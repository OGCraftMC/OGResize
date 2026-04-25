package org.ogcraft.ogresize.gui;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.ogcraft.ogresize.OGResize;
import org.ogcraft.ogresize.util.ScaleUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class ResizeListener implements Listener {

    // Runs for every inventory slot click
    @EventHandler
    public void onClick(InventoryClickEvent event) {

        // Check if plugin is enabled
        if (!OGResize.getInstance().isResizeEnabled()) {
            return;
        }

        // Ensures a player triggered it
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        // Filter 1: Only accept 1x9 (9 slot) inventories
        if (event.getInventory().getSize() != 9) {
            return;
        }

        // Filter 2: Only accept inventory by title
        if (!PlainTextComponentSerializer.plainText().serialize(event.getView().title()).equals("Change your player size")) {
            return;
        }

        // Stop item movement
        event.setCancelled(true);

        // Ignore empty clicks
        if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) {
            return;
        }

        // Safely handle item names
        var item = event.getCurrentItem();
        if (item == null) {
            return;
        }

        var meta = item.getItemMeta();
        if (meta == null) {
            return;
        }

        Component displayName = meta.displayName();
        if (displayName == null) {
            return;
        }

        // Convert display name to plain text for comparison
        String name = PlainTextComponentSerializer.plainText().serialize(displayName);

        // Handle click actions
        switch (name) {

            // Smaller option
            case "Smaller" -> {

                // Grab current size
                double current = ScaleUtil.getScale(player);

                // Prevent player exceeding minimum size & let them know
                if (current <= ScaleUtil.getMin()) {
                    player.sendMessage(msg("messages.min-size-limit"));
                    return;
                }

                // Decrease size safely
                double newSize = ScaleUtil.decrease(player);

                // Display to player
                player.sendMessage(msg("messages.smaller").replaceText(builder -> builder.matchLiteral("%size%").replacement(format(newSize))));
            }

            // Reset button
            case "Default" -> {
                ScaleUtil.reset(player);

                // Display to player
                player.sendMessage(msg("messages.reset"));
            }

            // Bigger option
            case "Bigger" -> {

                // Grab current size
                double current = ScaleUtil.getScale(player);

                // Prevent player exceeding maximum size & let them know
                if (current >= ScaleUtil.getMax()) {
                    player.sendMessage(msg("messages.max-size-limit"));
                    return;
                }

                // Decrease size safely
                double newSize = ScaleUtil.increase(player);

                // Display to player
                player.sendMessage(msg("messages.bigger").replaceText(builder -> builder.matchLiteral("%size%").replacement(format(newSize))));
            }
        }
    }

    // Message helper
    private Component msg(String path) {
        FileConfiguration config = OGResize.getInstance().getConfig();

        String prefix = config.getString("messages.prefix", "");
        String message = config.getString(path, "");

        return LegacyComponentSerializer.legacyAmpersand().deserialize(prefix + message);
    }

    private String format(double value) {
        return String.format("%.1f", value);
    }

}
