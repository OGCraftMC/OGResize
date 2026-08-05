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

        // Instant to minimum (Small)
        // Left most button
        ItemStack instasmall = new ItemStack(Material.CHERRY_BUTTON);
        ItemMeta ism = instasmall.getItemMeta();

        ism.displayName(Component.text("Smallest").color(NamedTextColor.BLUE));
        ism.lore(List.of(Component.text("Click to decrease size to minimum (min " + ScaleUtil.getMin() + ")").color(NamedTextColor.GRAY)));
        instasmall.setItemMeta(ism);

        // Slot 1,1 ; index 0
        gui.setItem(0, instasmall);


        // Small Increment
        // 2nd Left Button
        ItemStack smaller = new ItemStack(Material.CHERRY_SLAB);
        ItemMeta sm = smaller.getItemMeta();

        sm.displayName(Component.text("Smaller").color(NamedTextColor.BLUE));
        sm.lore(List.of(Component.text("Click to decrease size by " + ScaleUtil.getSTEP()).color(NamedTextColor.GRAY)));
        smaller.setItemMeta(sm);

        // Slot 1,3 ; index 2
        gui.setItem(2, smaller);


        // Reset to Default
        // Middle Button
        ItemStack reset = new ItemStack(Material.ARMOR_STAND);
        ItemMeta rm = reset.getItemMeta();

        rm.displayName(Component.text("Default").color(NamedTextColor.BLUE));
        rm.lore(List.of(Component.text("Reset to " + ScaleUtil.getDefault()).color(NamedTextColor.GRAY)));
        reset.setItemMeta(rm);

        // Slot 1,5 ; index 4
        gui.setItem(4, reset);


        // Large Increment
        // 2nd Right Button
        ItemStack bigger = new ItemStack(Material.CHERRY_STAIRS);
        ItemMeta bi = bigger.getItemMeta();

        bi.displayName(Component.text("Bigger").color(NamedTextColor.BLUE));
        bi.lore(List.of(Component.text("Click to increase size by " + ScaleUtil.getSTEP()).color(NamedTextColor.GRAY)));
        bigger.setItemMeta(bi);

        // Slot 1,7 ; index 6
        gui.setItem(6, bigger);


        // Instant to maximum (large)
        // Rightmost Button
        ItemStack instabig = new ItemStack(Material.CHERRY_PLANKS);
        ItemMeta ibi = instabig.getItemMeta();

        ibi.displayName(Component.text("Biggest").color(NamedTextColor.BLUE));
        ibi.lore(List.of(Component.text("Click to increase size to maximum (max " + ScaleUtil.getMax() + ")").color(NamedTextColor.GRAY)));
        instabig.setItemMeta(ibi);

        // Slot 1,9 ; index 8
        gui.setItem(8, instabig);


        // Opens GUI
        player.openInventory(gui);
    }
}
