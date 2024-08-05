package de.finnxd.qsg.gameloop.gamestates;

import de.finnxd.qsg.QSG;
import de.finnxd.qsg.gameloop.GameState;
import org.bson.Document;
import org.bukkit.entity.Player;

public class EndState implements GameState {
    private int remainingTime = 10;
    private final QSG plugin;

    public EndState(QSG plugin) {
        this.plugin = plugin;
    }

    @Override
    public void enter() {
        plugin.getServer().getOnlinePlayers().forEach(this::teleportPlayers);
        if(plugin.getGameManager().getWinner() != null) {
            Document winnerDocument = (Document) plugin.getGameManager().getWinner().getMetadata("qsg").getFirst().value();
            winnerDocument.replace("wins", winnerDocument.getInteger("wins") + 1);
            plugin.getServer().getOnlinePlayers().forEach(this::sendWinnerTitle);
        } else {
            plugin.getServer().getOnlinePlayers().forEach(this::sendTieTitle);
        }
    }

    @Override
    public void update() {
        remainingTime--;
        if(remainingTime == 1) {
            plugin.getServer().getOnlinePlayers().forEach(player -> player.kickPlayer("§cRunde Beendet"));
        }
        if(remainingTime <= 0) {
            plugin.getServer().shutdown();
        }
    }

    @Override
    public void exit() {}

    private void sendWinnerTitle(Player player) {
        player.sendTitle("§a" + plugin.getGameManager().getWinner().getName(), "§7gewonnen!");
    }

    private void teleportPlayers(Player player) {
        player.teleport(plugin.getConfigLoader().getLobbyLocation());
    }

    private void sendTieTitle(Player player) {
        player.sendTitle("§7Unentschieden", "");
    }

}
