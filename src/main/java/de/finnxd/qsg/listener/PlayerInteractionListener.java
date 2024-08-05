package de.finnxd.qsg.listener;

import de.finnxd.qsg.enums.LootTable;
import de.finnxd.qsg.utils.ItemBuilder;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.util.Random;

public class PlayerInteractionListener implements Listener {
    private final Random random = new Random();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getItem() != null) {
            if(event.getItem().getType() == Material.RED_DYE) {
                event.getPlayer().kickPlayer("§cVerlassen");
                return;
            }

            if(event.getItem().getType() == Material.GREEN_CANDLE) {
                openStatsInventory(event.getPlayer());
                return;
            }
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();

            if (block != null && block.getType() == Material.CHEST) {
                Chest chest = (Chest) block.getState();
                handleChestClick(chest);
            }
        }
    }

    private void openStatsInventory(Player player) {
        Document document = (Document) player.getMetadata("qsg").getFirst().value();
        Inventory inventory = Bukkit.createInventory(null, 9, "§aStats");
        inventory.setItem(2, new ItemBuilder(Material.IRON_SWORD).setDisplayName("§7Kills: §a" + document.getInteger("kills")).build());
        inventory.setItem(6, new ItemBuilder(Material.SKELETON_SKULL).setDisplayName("§7Tode: §c" + document.getInteger("deaths")).build());
        player.openInventory(inventory);
    }

    private void handleChestClick(Chest chest) {
        if (chest.getInventory().isEmpty()) {
            fillInventoryWithLoot(chest.getInventory());
        }
    }

    private void fillInventoryWithLoot(Inventory inventory) {
        for (LootTable loot : LootTable.values()) {
            if (shouldAddLoot(loot.getChance())) {
                ItemStack item = new ItemStack(loot.getType());
                inventory.addItem(item);
            }
        }
    }

    private boolean shouldAddLoot(int chance) {
        int randomNumber = random.nextInt(100) + 1;
        return randomNumber <= chance;
    }
}
