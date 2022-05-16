package me.cryptizism.tnttag.manager;

import org.bukkit.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Random;

public class RoundManager {
    public int gameRound;

    private GameManager gameManager;

    public RoundManager(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void nextRound(){
        int playerCount = gameManager.itController.getPlayersSize();
        if(playerCount == 0){
            Bukkit.broadcastMessage(ChatColor.RED + "Tried to give IT role but there are no players, report to event organiser (Cryptizism).");
            return;
        }
        Random rand = new Random();
        int rand_int = rand.nextInt(playerCount);
        OfflinePlayer[] players = gameManager.itController.PlayersTeamList().toArray(new OfflinePlayer[playerCount]);

        if (playerCount <= 6){

            gameManager.itController.addToIT(((Player) players[rand_int]), true);
            /*
            if(playerCount > 4){
                //teleport to middle
            }
            */
        } else if(playerCount <= 10){
            for (int i = 1; i <= 2; i++) {
                gameManager.itController.addToIT(((Player) players[rand_int]), true);
            }
        } else {
            for (int i = 1; i <= 4; i++) {
                gameManager.itController.addToIT(((Player) players[rand_int]), true);
            }
        }
        /*10 = 2 6 = 1  rest = 4*/
        // Every round is 5 less seconds
        int roundTime = 60 - (gameRound * 5);
    }

    public void initRounds(){
        //First round relaese from spawn and shitttt !!!!
    }
}
