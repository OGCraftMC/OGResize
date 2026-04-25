package org.ogcraft.ogresize.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ogcraft.ogresize.util.ScaleUtil;

import java.util.List;

public class ResizeGUI {

    public static final Component TITLE = Component.text("Change your player size").color(NamedTextColor.BLUE);

    public static void open(Player player) {

        // Create inventory GUI
        Inventory gui = Bukkit.createInventory(null, 9, TITLE);

        // LEFT
        // Smaller Option ; Cherry Wood Button
        ItemStack smaller = new ItemStack(Material.CHERRY_BUTTON);
        ItemMeta sm = smaller.getItemMeta();

        sm.displayName(Component.text("Smaller").color(NamedTextColor.BLUE));
        sm.lore(List.of(Component.text("Click to decrease size (min " + ScaleUtil.getMin() + ")").color(NamedTextColor.GRAY)));
        smaller.setItemMeta(sm);

        // Slot 1,3 ; index 2
        gui.setItem(2, smaller);

        // MIDDLE
        // Reset to Default ; Default = 1.0 = 2 blocks tall ; Armor Stand
        ItemStack reset = new ItemStack(Material.ARMOR_STAND);
        ItemMeta rm = reset.getItemMeta();

        rm.displayName(Component.text("Default").color(NamedTextColor.BLUE));
        rm.lore(List.of(Component.text("Reset to " + ScaleUtil.getDefault()).color(NamedTextColor.GRAY)));
        reset.setItemMeta(rm);

        // Slot 1,5 ; index 4
        gui.setItem(4, reset);

        // RIGHT
        // Bigger Option ; Cherry Wood Plank
        ItemStack bigger = new ItemStack(Material.CHERRY_PLANKS);
        ItemMeta bi = bigger.getItemMeta();

        bi.displayName(Component.text("Bigger").color(NamedTextColor.BLUE));
        bi.lore(List.of(Component.text("Click to increase size (max " + ScaleUtil.getMax() + ")").color(NamedTextColor.GRAY)));
        bigger.setItemMeta(bi);

        // Slot 1,7 ; index 6
        gui.setItem(6, bigger);

        // Opens GUI
        player.openInventory(gui);
    }
}
