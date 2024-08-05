package de.finnxd.qsg.listener;

import de.finnxd.qsg.QSG;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerKillListener implements Listener {
    private final QSG plugin;

    public PlayerKillListener(QSG plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = event.getEntity().getKiller();
        Document playerDocument = (Document) player.getMetadata("qsg").getFirst().value();

        if (killer != null) {
            Document killerDocument = (Document) killer.getMetadata("qsg").getFirst().value();
            killerDocument.replace("kills", killerDocument.getInteger("kills") + 1);
            event.setDeathMessage("§c" + player.getName() + " §7wurde von §c" + killer.getName() + " §7getötet!");
            plugin.getScoreboardBuilder().updateKillValue(killer);
        } else {
            event.setDeathMessage("§c" + player.getName() + " §7ist gestorben!");
        }

        playerDocument.replace("deaths", playerDocument.getInteger("deaths") + 1);
        plugin.getGameManager().getPlayerList().remove(player);
        player.kickPlayer("§cVerloren");
    }

}
