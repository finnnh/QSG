package de.finnxd.qsg.gameloop.gamestates;

import de.finnxd.qsg.QSG;
import de.finnxd.qsg.enums.GracePeriodeState;
import de.finnxd.qsg.gameloop.GameState;
import de.finnxd.qsg.gameloop.GameStateType;
import de.finnxd.qsg.utils.ItemBuilder;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class IngameState implements GameState {
    private final QSG plugin;
    private int graceTime = 10;
    private int remainingTime = 60*5;

    public IngameState(QSG plugin) {
        this.plugin = plugin;
    }

    @Override
    public void enter() {
        plugin.getGameManager().getPlayerList().forEach(this::enterPlayerIngame);
    }

    @Override
    public void update() {
        if(plugin.getGameManager().getCurrentGracePeriodeState() == GracePeriodeState.ACTIVE) {
            graceTime--;
            plugin.getServer().getOnlinePlayers().forEach(this::sendGraceTime);
            if(graceTime <= 0) {
                plugin.getGameManager().setCurrentGracePeriodeState(GracePeriodeState.INACTIVE);
            }
            return;
        }

        remainingTime--;
        if(plugin.getGameManager().getPlayerList().isEmpty() || remainingTime == 0) {
            plugin.getGameStateManager().switchGameStateType(GameStateType.END);
            return;
        }
        if(plugin.getGameManager().getPlayerList().size() == 1) {
            plugin.getGameManager().setWinner(plugin.getGameManager().getPlayerList().get(0));
            plugin.getGameStateManager().switchGameStateType(GameStateType.END);
            return;
        }
        plugin.getGameManager().getPlayerList().forEach(this::sendActionBarTimer);
    }

    @Override
    public void exit() {
        plugin.getServer().getOnlinePlayers().forEach(this::exitPlayerIngame);
    }

    private void enterPlayerIngame(Player player) {
        List<Location> locations = plugin.getConfigLoader().getMapLocations();
        for(int i = 0; i < plugin.getGameManager().getPlayerList().size(); i++) {
            plugin.getGameManager().getPlayerList().get(i).teleport(locations.get(i));
        }

        player.sendMessage("§aStart!");
    }

    private void exitPlayerIngame(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(8, new ItemBuilder(Material.RED_DYE).setDisplayName("§cVerlassen").build());
    }

    private void sendGraceTime(Player player) {
        player.sendMessage("§7Gnadenzeit ist noch für §a" + graceTime + "§7 Sekunden aktiv!");
    }

    private void sendActionBarTimer(Player player) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§7Verbleibende Zeit: §e" + formatSeconds(remainingTime)));
    }

    private String formatSeconds(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
