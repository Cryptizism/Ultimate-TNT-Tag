package me.cryptizism.tnttag.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.metadata.FixedMetadataValue;

public class HologramScoreboardManager {
    private static GameManager gameManager;
    ArmorStand[] holograms = new ArmorStand[6];
    Location loc;
    private FileConfiguration config;

    World world = Bukkit.getServer().getWorld("world");

    public HologramScoreboardManager(GameManager gameManager){
        this.gameManager = gameManager;
        config = gameManager.plugin.getConfig();
    }

    public void createHoloScoreboard(){
        setHoloLocation();
        for(int i = 0; i < holograms.length; i++){
            double y = this.loc.getY() + (i*0.25);
            Location spawnLocation = new Location(world, this.loc.getX(), y, this.loc.getZ());
            holograms[i] = (ArmorStand) world.spawnEntity(spawnLocation, EntityType.ARMOR_STAND);
            holograms[i].setGravity(false);
            holograms[i].setVisible(false);
            holograms[i].setSmall(true);
            holograms[i].setCustomNameVisible(true);
            holograms[i].setCustomName("");
            holograms[i].setMetadata("Type", new FixedMetadataValue(gameManager.plugin, "scoreboard"));
        }
    }

    public void setHoloLocation(){
        World world = Bukkit.getServer().getWorld("world");
        try{
            double x = (double) config.get("holo-coords.x");
            double y = (double) config.get("holo-coords.y");
            double z = (double) config.get("holo-coords.z");
            loc = new Location(world, x, y, z);
        } catch(NumberFormatException e){
            Bukkit.getLogger().info("Could not get location for holo");
            //fallback
            loc = gameManager.spawningManager.lobby;
        }
    }

    public void updateHolo(){
        String[] scoreboardText = gameManager.mySQLQueries.getTopPlayers(holograms.length-1);
        int i = scoreboardText.length-1;
        for (String text : scoreboardText) {
            holograms[i].setCustomName(text);
            i--;
        }
    }

    public void setTitle(String title){
        holograms[holograms.length-1].setCustomName(title);
        updateHolo();
    }
}
