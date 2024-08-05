package de.finnxd.qsg.listener;

import de.finnxd.qsg.utils.ItemBuilder;
import de.finnxd.qsg.QSG;
import de.finnxd.qsg.gameloop.GameStateType;
import org.bson.Document;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerConnectionListener implements Listener {
    private final QSG plugin;

    public PlayerConnectionListener(QSG plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage("§a» " + event.getPlayer().getName());
        event.getPlayer().getInventory().clear();
        event.getPlayer().getEquipment().clear();
        event.getPlayer().getActivePotionEffects().clear();
        event.getPlayer().setGameMode(GameMode.SURVIVAL);

        event.getPlayer().teleport(plugin.getConfigLoader().getLobbyLocation());

        Document document = plugin.getDatabaseConnector().createPlayerIfNotExists(event.getPlayer().getUniqueId());
        event.getPlayer().setMetadata("qsg", new FixedMetadataValue(plugin, document));

        plugin.getGameManager().getPlayerList().add(event.getPlayer());
        event.getPlayer().getInventory().setItem(0, new ItemBuilder(Material.GREEN_CANDLE).setDisplayName("§aStats").build());
        event.getPlayer().getInventory().setItem(8, new ItemBuilder(Material.RED_DYE).setDisplayName("§cVerlassen").build());
        plugin.getScoreboardBuilder().setScoreboard(event.getPlayer());
        plugin.getScoreboardBuilder().updatePlayerAmountValue();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage("§c« " + event.getPlayer().getName());
        plugin.getGameManager().getPlayerList().remove(event.getPlayer());

        if(plugin.getGameStateManager().getCurrentGameStateType() == GameStateType.INGAME) {
            Document playerDocument = (Document) event.getPlayer().getMetadata("qsg").getFirst().value();
            playerDocument.replace("deaths", playerDocument.getInteger("deaths") + 1);
            plugin.getDatabaseConnector().updatePlayer(playerDocument);
        }

        if(plugin.getGameStateManager().getCurrentGameStateType() == GameStateType.END) {
            Document playerDocument = (Document) event.getPlayer().getMetadata("qsg").getFirst().value();
            plugin.getDatabaseConnector().updatePlayer(playerDocument);
        }
        event.getPlayer().removeMetadata("qsg", plugin);
        plugin.getScoreboardBuilder().updatePlayerAmountValue();
    }

}
