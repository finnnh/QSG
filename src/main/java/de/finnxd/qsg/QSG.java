package de.finnxd.qsg;

import de.finnxd.qsg.config.ConfigLoader;
import de.finnxd.qsg.database.DatabaseConnector;
import de.finnxd.qsg.gameloop.GameManager;
import de.finnxd.qsg.gameloop.GameStateManager;
import de.finnxd.qsg.listener.PlayerCancelListener;
import de.finnxd.qsg.listener.PlayerConnectionListener;
import de.finnxd.qsg.listener.PlayerKillListener;
import de.finnxd.qsg.listener.PlayerInteractionListener;
import de.finnxd.qsg.utils.ScoreboardBuilder;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

public final class QSG extends JavaPlugin {
    private GameStateManager gameStateManager;
    private GameManager gameManager;
    private DatabaseConnector databaseConnector;
    private ConfigLoader configLoader;
    private ScoreboardBuilder scoreboardBuilder;

    @Override
    public void onEnable() {
        this.configLoader = new ConfigLoader();
        this.databaseConnector = new DatabaseConnector();
        this.gameStateManager = new GameStateManager(this);
        this.gameManager = new GameManager();
        this.scoreboardBuilder = new ScoreboardBuilder(this);

        registerListener();
        Bukkit.createWorld(new WorldCreator(configLoader.getCurrentConfig().lobby_location().worldName()));
    }

    private void registerListener() {
        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerKillListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractionListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerCancelListener(this), this);
    }

    @Override
    public void onDisable() {
        databaseConnector.close();
    }

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public DatabaseConnector getDatabaseConnector() {
        return databaseConnector;
    }

    public ConfigLoader getConfigLoader() {
        return configLoader;
    }

    public ScoreboardBuilder getScoreboardBuilder() {
        return scoreboardBuilder;
    }
}
