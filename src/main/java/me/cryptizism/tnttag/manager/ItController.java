package me.cryptizism.tnttag.manager;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Set;

import org.bukkit.inventory.ItemStack;

public class ItController {
    private static GameManager gameManager;

    //public static Team ITTeam = ScoreboardManager.board.getTeam("IT");
    //public static Team PlayersTeam = ScoreboardManager.board.getTeam("Players");

    public ItController(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void removeITPlayer(Player player) {
        ScoreboardManager.board.getTeam("Players").addEntry(player.getName());
        player.getInventory().clear();
        player.getInventory().setHelmet(new ItemStack(Material.AIR));
    }

    public void addITPlayer(Player player){
        ScoreboardManager.board.getTeam("IT").addEntry(player.getName());
        player.getInventory().clear();
        player.getInventory().setItem(0, new ItemStack(Material.TNT));
        //Player tracking compass
        player.getInventory().setItem(1, new ItemStack(Material.COMPASS));
        player.getInventory().setHelmet(new ItemStack(Material.TNT));
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
}
