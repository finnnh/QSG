package de.finnxd.qsg.config;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigLoader {
    private final Gson gson = new Gson();
    private ConfigJSON currentConfig;

    public ConfigLoader() {
        loadConfig();
    }

    public void loadConfig() {
        File configFile = new File("./plugins/QSG/config.json");

        if (configFile.exists()) {
            try (FileReader fileReader = new FileReader(configFile)) {
                currentConfig = gson.fromJson(fileReader, ConfigJSON.class);
            } catch (IOException | JsonSyntaxException exception) {
                exception.printStackTrace();
            }
        }
    }

    public ConfigJSON getCurrentConfig() {
        return currentConfig;
    }

    public Location getLobbyLocation() {
        return convertConfigLocationToBukkitLocation(currentConfig.lobby_location());
    }

    public List<Location> getMapLocations() {
        ConfigLocation[] configLocations = currentConfig.map_locations();
        return Arrays.stream(configLocations).map(this::convertConfigLocationToBukkitLocation)
                .collect(Collectors.toList());
    }

    private Location convertConfigLocationToBukkitLocation(ConfigLocation configLocation) {
        return new Location(Bukkit.getWorld(configLocation.worldName())
                , configLocation.x(), configLocation.y(), configLocation.z()
                , configLocation.yaw(), configLocation.pitch());
    }

}
