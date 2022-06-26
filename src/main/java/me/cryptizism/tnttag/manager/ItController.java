package me.cryptizism.tnttag.manager;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItController {
    private static GameManager gameManager;

    //public static Team ITTeam = ScoreboardManager.board.getTeam("IT");
    //public static Team PlayersTeam = ScoreboardManager.board.getTeam("Players");

    public ItController(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void addToPlayer(Player player) {
        //Adds to team
        ScoreboardManager.board.getTeam("Players").addEntry(player.getName());
        //clears previous inventory changes
        player.getInventory().clear();
        player.getInventory().setHelmet(new ItemStack(Material.AIR));
        //sets gamemode
        player.setGameMode(GameMode.SURVIVAL);
        //Clear speed
        player.removePotionEffect(PotionEffectType.SPEED);
        //Give speed
        PotionEffect swiftness = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false);
        player.addPotionEffect(swiftness);
    }

    public void addToIT(Player player, boolean firstRound){
        if(!firstRound){
            Bukkit.broadcastMessage(player.getDisplayName() + " is IT!");
        }
        //Adds to team
        ScoreboardManager.board.getTeam("IT").addEntry(player.getName());

        //clearing anything from prior
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);

        //Sets up the inventory
        player.getInventory().setItem(0, new ItemStack(Material.TNT));
        player.getInventory().setHelmet(new ItemStack(Material.TNT));

        //Player tracking compass
        player.getInventory().setItem(1, new ItemStack(Material.COMPASS));

        //Let off firework
        Location playerLoc = player.getLocation();
        Firework firework = (Firework) playerLoc.getWorld().spawnEntity(playerLoc, EntityType.FIREWORK);
        FireworkMeta fwm = firework.getFireworkMeta();
        fwm.addEffect(FireworkEffect.builder().withColor(Color.GREEN).with(FireworkEffect.Type.CREEPER).build());
        fwm.setPower(0);
        firework.setFireworkMeta(fwm);
        firework.playEffect(EntityEffect.FIREWORK_EXPLODE);

        //Action bar title

        //Play sound
        player.playSound(playerLoc, Sound.NOTE_PLING, 4, 2);

        //Give speed
        player.removePotionEffect(PotionEffectType.SPEED);
        PotionEffect swiftness = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3, false, false);
        player.addPotionEffect(swiftness);
    }

    public void addToSpec(Player player){
        ScoreboardManager.board.getTeam("Spectators").addEntry(player.getName());
        player.getInventory().clear();
        player.getInventory().setHelmet(new ItemStack(Material.AIR));
        player.setGameMode(GameMode.SPECTATOR);
        addLastKilled(player);
    }

    public int getITTeamSize(){
        return ScoreboardManager.board.getTeam("IT").getSize();
    }

    public int getPlayersSize(){
        return ScoreboardManager.board.getTeam("Players").getSize();
    }

    public Set<OfflinePlayer> ITTeamList(){
        return ScoreboardManager.board.getTeam("IT").getPlayers();
    }

    public Set<OfflinePlayer> PlayersTeamList(){
        return ScoreboardManager.board.getTeam("Players").getPlayers();
    }

    public void addAllToPlayers(){
        Bukkit.getOnlinePlayers().forEach(player -> gameManager.itController.addToPlayer(player));
    }

    public Set<OfflinePlayer> InGameList(){
        return new HashSet<OfflinePlayer>() {
            {
                addAll(ITTeamList());
                addAll(PlayersTeamList());
            }
        };
    }

    public Set<Player> lastKilled = new HashSet<Player>();

    public Set<Player> getLastKilled() {
        return lastKilled;
    }

    public void addLastKilled(Player player) {
        this.lastKilled.add(player);
    }

    public void clearLastKilled(){
        this.lastKilled.clear();
    }
}
