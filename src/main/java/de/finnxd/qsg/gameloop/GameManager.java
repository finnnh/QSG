package de.finnxd.qsg.gameloop;

import de.finnxd.qsg.enums.GracePeriodeState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private final List<Player> playerList = new ArrayList<>();
    private Scoreboard scoreboard;
    private Player winner = null;
    private GracePeriodeState currentGracePeriodeState = GracePeriodeState.ACTIVE;

    public List<Player> getPlayerList() {
        return playerList;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public GracePeriodeState getCurrentGracePeriodeState() {
        return currentGracePeriodeState;
    }

    public void setCurrentGracePeriodeState(GracePeriodeState currentGracePeriodeState) {
        this.currentGracePeriodeState = currentGracePeriodeState;
    }

}
