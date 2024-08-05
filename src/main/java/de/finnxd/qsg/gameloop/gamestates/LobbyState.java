package de.finnxd.qsg.gameloop.gamestates;

import de.finnxd.qsg.QSG;
import de.finnxd.qsg.gameloop.GameState;
import de.finnxd.qsg.gameloop.GameStateType;
import org.bukkit.entity.Player;

public class LobbyState implements GameState {
    private final QSG plugin;
    private final int startTime = 30;
    private int remainingTime = startTime;
    private int minimumPlayers = 2;

    public LobbyState(QSG plugin) {
        this.plugin = plugin;
    }

    @Override
    public void enter() {}

    @Override
    public void update() {
        if(remainingTime == 0) {
            plugin.getGameStateManager().switchGameStateType(GameStateType.INGAME);
            return;
        }
        if(plugin.getGameManager().getPlayerList().size() >= minimumPlayers) {
            remainingTime--;
        } else {
            remainingTime = startTime;
        }
        plugin.getGameManager().getPlayerList().forEach(this::doUpdate);
    }

    @Override
    public void exit() {
        plugin.getGameManager().getPlayerList().forEach(this::exitPlayer);
    }

    private void doUpdate(Player player) {
        player.setLevel(remainingTime);
    }

    private void exitPlayer(Player player) {
        player.getInventory().clear();
        player.getEquipment().clear();
        player.setLevel(0);
    }
}
