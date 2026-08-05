package org.ogcraft.ogresize.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
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

            // Instant Minimum
            case "Smallest" -> {

                // Grab current size
                double current = ScaleUtil.getScale(player);

                // Prevent player exceeding minimum size & let them know
                if (current <= ScaleUtil.getMin()) {
                    player.sendMessage(OGResize.getInstance().msg("messages.min-size-limit"));
                    return;
                }

                // Set size to minimum safely
                ScaleUtil.ResizeResult result = ScaleUtil.setExactScale(player, ScaleUtil.getMin());

                // Kill resize if validation fails
                if (!result.success()) {
                    player.sendMessage(OGResize.getInstance().msg("messages.no-room"));
                    return;
                }

                // Display to player
                player.sendMessage(OGResize.getInstance().msgComponent("messages.smaller").replaceText(builder -> builder.matchLiteral("%size%").replacement(OGResize.getInstance().format(result.newSize()))));

            }

            // Smaller option
            case "Smaller" -> {

                // Grab current size
                double current = ScaleUtil.getScale(player);

                // Prevent player exceeding minimum size & let them know
                if (current <= ScaleUtil.getMin()) {
                    player.sendMessage(OGResize.getInstance().msg("messages.min-size-limit"));
                    return;
                }

                // Decrease size safely
                ScaleUtil.ResizeResult result = ScaleUtil.decrease(player);

                // Kill resize if validation fails
                if (!result.success()) {
                    player.sendMessage(OGResize.getInstance().msg("messages.no-room"));
                    return;
                }

                // Display to player
                player.sendMessage(OGResize.getInstance().msgComponent("messages.smaller").replaceText(builder -> builder.matchLiteral("%size%").replacement(OGResize.getInstance().format(result.newSize()))));
            }

            // Reset button
            case "Default" -> {

                // Reset size safely
                boolean result = ScaleUtil.reset(player);

                // Kill reset if validation fails
                if (!result) {
                    player.sendMessage(OGResize.getInstance().msg("messages.no-room"));
                    return;
                }

                // Display to player
                player.sendMessage(OGResize.getInstance().msg("messages.reset"));
            }

            // Bigger option
            case "Bigger" -> {

                // Grab current size
                double current = ScaleUtil.getScale(player);

                // Prevent player from exceeding maximum size & let them know
                if (current >= ScaleUtil.getMax()) {
                    player.sendMessage(OGResize.getInstance().msg("messages.max-size-limit"));
                    return;
                }

                // Increase size safely
                ScaleUtil.ResizeResult result = ScaleUtil.increase(player);

                // Kill resize if validation fails
                if (!result.success()) {
                    player.sendMessage(OGResize.getInstance().msg("messages.no-room"));
                    return;
                }

                // Display to player
                player.sendMessage(OGResize.getInstance().msgComponent("messages.bigger").replaceText(builder -> builder.matchLiteral("%size%").replacement(OGResize.getInstance().format(result.newSize()))));
            }

            case "Biggest" -> {

                // Grab current size
                double current = ScaleUtil.getScale(player);

                // Prevent player from exceeding maximum size & let them know
                if (current >= ScaleUtil.getMax()) {
                    player.sendMessage(OGResize.getInstance().msg("messages.max-size-limit"));
                    return;
                }

                // Set size to maximum safely
                ScaleUtil.ResizeResult result = ScaleUtil.setExactScale(player, ScaleUtil.getMax());

                // Kill resize if validation fails
                if (!result.success()) {
                    player.sendMessage(OGResize.getInstance().msg("messages.no-room"));
                    return;
                }

                // Display to player
                player.sendMessage(OGResize.getInstance().msgComponent("messages.bigger").replaceText(builder -> builder.matchLiteral("%size%").replacement(OGResize.getInstance().format(result.newSize()))));

            }

        }
    }
}
