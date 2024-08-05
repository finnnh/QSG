package de.finnxd.qsg.listener;

import de.finnxd.qsg.QSG;
import de.finnxd.qsg.gameloop.GameStateType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class PlayerCancelListener implements Listener {
    private final QSG plugin;

    public PlayerCancelListener(QSG plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (plugin.getGameStateManager().getCurrentGameStateType() != GameStateType.INGAME) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleCreatureSpawn(CreatureSpawnEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void handleInventoryClick(InventoryClickEvent event){
        if (plugin.getGameStateManager().getCurrentGameStateType() != GameStateType.INGAME) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleWeatherChange(WeatherChangeEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerSwapItem(PlayerSwapHandItemsEvent event) {
        if (plugin.getGameStateManager().getCurrentGameStateType() != GameStateType.INGAME) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (plugin.getGameStateManager().getCurrentGameStateType() != GameStateType.INGAME) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (plugin.getGameStateManager().getCurrentGameStateType() != GameStateType.INGAME) {
            event.setCancelled(true);
        }
    }

}
