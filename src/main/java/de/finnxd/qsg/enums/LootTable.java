package de.finnxd.qsg.enums;

import org.bukkit.Material;

public enum LootTable {
    DIAMOND_SWORD(Material.DIAMOND_SWORD, 5),
    IRON_SWORD(Material.IRON_SWORD, 10),
    BOW(Material.BOW, 8),
    ARROW(Material.ARROW, 15),
    GOLDEN_APPLE(Material.GOLDEN_APPLE, 7),
    LEATHER_HELMET(Material.LEATHER_HELMET, 10),
    IRON_HELMET(Material.IRON_HELMET, 8),
    DIAMOND_HELMET(Material.DIAMOND_HELMET, 4),
    LEATHER_CHESTPLATE(Material.LEATHER_CHESTPLATE, 9),
    IRON_CHESTPLATE(Material.IRON_CHESTPLATE, 7),
    DIAMOND_CHESTPLATE(Material.DIAMOND_CHESTPLATE, 3),
    LEATHER_LEGGINGS(Material.LEATHER_LEGGINGS, 9),
    IRON_LEGGINGS(Material.IRON_LEGGINGS, 7),
    DIAMOND_LEGGINGS(Material.DIAMOND_LEGGINGS, 3),
    LEATHER_BOOTS(Material.LEATHER_BOOTS, 9),
    IRON_BOOTS(Material.IRON_BOOTS, 7),
    DIAMOND_BOOTS(Material.DIAMOND_BOOTS, 3),
    APPLE(Material.APPLE, 20),
    BREAD(Material.BREAD, 15),
    COOKED_BEEF(Material.COOKED_BEEF, 12),
    COOKED_CHICKEN(Material.COOKED_CHICKEN, 10);

    private final Material type;
    private final int chance;

    LootTable(Material type, int chance) {
        this.type = type;
        this.chance = chance;
    }

    public Material getType() {
        return type;
    }

    public int getChance() {
        return chance;
    }
}