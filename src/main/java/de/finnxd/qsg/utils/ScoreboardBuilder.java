package de.finnxd.qsg.utils;

import de.finnxd.qsg.QSG;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardBuilder {
    private final QSG plugin;

    public ScoreboardBuilder(QSG plugin) {
        this.plugin = plugin;
    }

    public void setScoreboard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("§7-= §cQSG §7=-", "qsg");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        objective.getScore(" ").setScore(6);
        objective.getScore("§7Spieler:").setScore(5);
        objective.getScore(this.createStringTeam(scoreboard, "playerAmount", "§c" + "0/4", ChatColor.RED)).setScore(4);
        objective.getScore("  ").setScore(3);
        objective.getScore("§7Kills:").setScore(2);
        objective.getScore(this.createStringTeam(scoreboard, "kills", "§c" + 0, ChatColor.BLUE)).setScore(1);
        objective.getScore("   ").setScore(0);

        player.setScoreboard(scoreboard);
    }

    private String createStringTeam(Scoreboard scoreboard, String teamName, String prefix, ChatColor color) {
        Team team = scoreboard.getTeam(teamName);
        if(team == null) {
            team = scoreboard.registerNewTeam(teamName);
        }
        team.setPrefix(prefix);
        team.addEntry(color.toString());
        return color.toString();
    }

    public void updateKillValue(Player player) {
        if(player.getScoreboard().getTeam("kills") != null) {
            player.getScoreboard().getTeam("kills").setPrefix("§c" + ((Document)player.getMetadata("qsg").getFirst().value()).getInteger("kills"));
        }
    }

    public void updatePlayerAmountValue() {
        for(Player player : plugin.getGameManager().getPlayerList()) {
            if(player.getScoreboard().getTeam("playerAmount") != null) {
                player.getScoreboard().getTeam("playerAmount").setPrefix("§c" + plugin.getGameManager().getPlayerList().size() + "/4");
            }
        }
    }

}
