package org.ogcraft.ogresize.util;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class JoinListener implements Listener {

    // Re-applies saved player size
    // Triggers when player joins
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        apply(event.getPlayer());
    }

    // Re-applies saved player size
    // Triggers when player respawns
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        apply(event.getPlayer());
    }

    // Applies stored size to player
    private void apply(Player player) {

        // Grabs saved size from playersizes.yml using UUID
        double size = ResizePersistence.getSize(player.getUniqueId());

        // Safety Check
        if (size <= 0) {
            size = 1.0;
        }

        // Apply size
        ScaleUtil.setScale(player, size);
    }
}
