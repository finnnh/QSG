package de.finnxd.qsg.gameloop;

import de.finnxd.qsg.QSG;
import de.finnxd.qsg.gameloop.gamestates.EndState;
import de.finnxd.qsg.gameloop.gamestates.IngameState;
import de.finnxd.qsg.gameloop.gamestates.LobbyState;
import org.bukkit.Bukkit;

import java.util.HashMap;

public class GameStateManager {
    private final QSG plugin;
    private GameStateType currentGameStateType = GameStateType.LOBBY;
    private final HashMap<GameStateType, GameState> gameStates = new HashMap<>();

    public GameStateManager(QSG plugin) {
        this.plugin = plugin;
        registerState(GameStateType.LOBBY, new LobbyState(plugin));
        registerState(GameStateType.INGAME, new IngameState(plugin));
        registerState(GameStateType.END, new EndState(plugin));
        startUpdate();
    }

    public void switchGameStateType(GameStateType newGameStateType) {
        gameStates.get(currentGameStateType).exit();
        currentGameStateType = newGameStateType;
        gameStates.get(currentGameStateType).enter();
    }

    private void registerState(GameStateType gameStateType, GameState gameState) {
        gameStates.put(gameStateType, gameState);
    }

    private void startUpdate() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            gameStates.get(currentGameStateType).update();
        }, 0L, 20L);
    }

    public GameStateType getCurrentGameStateType() {
        return currentGameStateType;
    }
}
