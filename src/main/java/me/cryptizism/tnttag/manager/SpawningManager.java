package me.cryptizism.tnttag.manager;

import me.cryptizism.tnttag.TntTag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.MissingResourceException;

public class SpawningManager {
    FileConfiguration config;
    public Location lobby;
    public Location game;

    public SpawningManager(FileConfiguration _config) {
        this.config = _config;
        setLobbyLocation();
        setGameLocation();
    }
    public void teleportToLobby(Player player){
        player.teleport(lobby);
        player.getInventory().clear();
    }

    public void setLobbyLocation() /*throws MissingResourceException*/ {
        World world = Bukkit.getServer().getWorld("world");
        double x = (double) config.get("waiting-coords.x");
        double y = (double) config.get("waiting-coords.y");
        double z = (double) config.get("waiting-coords.z");
        this.lobby = new Location(world, x, y, z);
        //throw new MissingResourceException("Could not get config location", "getLobbyLocation()", "MISSING_CONFIG_LOCATION");
    }

    public void teleportToGame(Player player){
        player.teleport(game);
        player.getInventory().clear();
    }

    public void setGameLocation(){
        World world = Bukkit.getServer().getWorld("world");
        double x = (double) config.get("spawn-coords.x");
        double y = (double) config.get("spawn-coords.y");
        double z = (double) config.get("spawn-coords.z");
        this.game = new Location(world, x, y, z);
    }
}
