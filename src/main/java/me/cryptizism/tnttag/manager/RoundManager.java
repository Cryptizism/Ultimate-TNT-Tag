package me.cryptizism.tnttag.manager;

import me.cryptizism.tnttag.TntTag;
import org.bukkit.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class RoundManager {
    public int gameRound = 0;
    public int roundTime = 60;

    private GameManager gameManager;

    public RoundManager(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void nextRound(){
        gameRound++;
        int playerCount = gameManager.itController.getPlayersSize();
        if(playerCount <= 1){
            Bukkit.broadcastMessage(ChatColor.RED + "Tried to give IT role but there are no players or 1 player, report to event organiser (Cryptizism).");
            return;
        }
        Random rand = new Random();
        int rand_int = rand.nextInt(playerCount);
        OfflinePlayer[] players = gameManager.itController.PlayersTeamList().toArray(new OfflinePlayer[playerCount]);

        if (playerCount <= 6){

            gameManager.itController.addToIT(((Player) players[rand_int]), true);

            if(playerCount > 4){
                gameManager.itController.PlayersTeamList().forEach(
                        player ->
                        gameManager.spawningManager.teleportToGame((Player) player)
                );
            }

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
        roundTime = 60 - (gameRound * 5);
        runnable();
    }

    public void initRounds(){
        if(Bukkit.getOnlinePlayers().size() <= 1){
            Bukkit.broadcastMessage(ChatColor.RED + "Cannot start game/rounds with only 1 player, report to event organiser (Cryptizism).");
            return;
        }
        if(gameRound == 0){
            gameManager.itController.addAllToPlayers();
        }
        gameManager.setGameState(GameState.ACTIVE);
        nextRound();
    }

    public void runnable(){
        new BukkitRunnable(){

            @Override
            public void run(){
                roundTime = roundTime - 1;
                if((roundTime < 0) || gameManager.gameState != GameState.ACTIVE ){
                    if(gameManager.itController.getPlayersSize() > 1) {
                        Bukkit.broadcastMessage(ChatColor.RED + "The round is over");
                    }
                    gameManager.itController.ITTeamList().forEach(player -> explode(player));
                    roundEnded();
                    cancel();
                } else {
                    gameManager.scoreboardManager.addToScoreboard(roundTime, gameRound);
                }
            }

        }.runTaskTimer(TntTag.getInstance(), 0, 20);
    }

    public void explode(OfflinePlayer oPlayer){
        Player player = (Player) oPlayer;
        Location playerLoc = player.getLocation();
        World world = player.getWorld();
        world.createExplosion(playerLoc.getX(), playerLoc.getY(), playerLoc.getZ(), 3, false, false);
        //Kill players based on location
        gameManager.itController.addToSpec(player);
        Bukkit.broadcastMessage(ChatColor.RED + player.getName() + " exploded.");
    }

    public void roundEnded(){
        if(gameManager.itController.getPlayersSize() > 1){
            initRounds();
        } else {
            Bukkit.broadcastMessage(gameManager.itController.PlayersTeamList().iterator().next().getName() + " has won!");
            gameManager.setGameState(GameState.LOBBY);
            gameRound = 0;
            roundTime = 0;
            gameManager.scoreboardManager.addToScoreboard(roundTime, gameRound);
        }
    }
}
