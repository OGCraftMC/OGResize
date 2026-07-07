package org.ogcraft.ogresize.util;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;


public final class CollisionValidator {

    // Utility class
    private CollisionValidator() {

    }

    // Check if resize safe
    public static boolean canResize(Player player, double newScale) {

        // Vehicle Check
        if (player.isInsideVehicle()) {
            return false;
        }

        // Convert bukkit player to NMS entity
        CraftPlayer craftPlayer = (CraftPlayer) player;
        net.minecraft.world.entity.player.Player nmsPlayer = craftPlayer.getHandle();

        // Get current scale
        double currentScale = ScaleUtil.getScale(player);

        // Get current bounding box
        double currentWidth = nmsPlayer.getBbWidth();
        double currentHeight = nmsPlayer.getBbHeight();

        // Calculate future dimensions based on scale difference
        double width = currentWidth * (newScale / currentScale);
        double height = currentHeight * (newScale / currentScale);
        double halfWidth = width / 2.0;

        // Get current position
        double x = nmsPlayer.getX();
        double y = nmsPlayer.getY();
        double z = nmsPlayer.getZ();

        // Create future bounding box after resizing
        AABB futureBox = new AABB(x - halfWidth, y, z - halfWidth, x + halfWidth, y + height, z + halfWidth);
        Level level = nmsPlayer.level();

        // Minecraft collision system checks if box fits
        return level.noCollision(nmsPlayer, futureBox);

    }
}
