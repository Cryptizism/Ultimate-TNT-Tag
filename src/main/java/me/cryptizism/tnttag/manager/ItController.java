package me.cryptizism.tnttag.manager;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;

import java.util.Set;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public class ItController {
    private static GameManager gameManager;

    //public static Team ITTeam = ScoreboardManager.board.getTeam("IT");
    //public static Team PlayersTeam = ScoreboardManager.board.getTeam("Players");

    public ItController(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void addToPlayer(Player player) {
        ScoreboardManager.board.getTeam("Players").addEntry(player.getName());
        player.getInventory().clear();
        player.getInventory().setHelmet(new ItemStack(Material.AIR));
        player.setGameMode(GameMode.SURVIVAL);
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
        player.playNote(playerLoc, Instrument.PIANO, Note.natural(1, Note.Tone.D));
    }

    public void addToSpec(Player player){
        ScoreboardManager.board.getTeam("Spectators").addEntry(player.getName());
        player.getInventory().clear();
        player.getInventory().setHelmet(new ItemStack(Material.AIR));
        player.setGameMode(GameMode.SPECTATOR);
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
}
