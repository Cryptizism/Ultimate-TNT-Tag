package me.cryptizism.tnttag.manager;

import me.cryptizism.tnttag.TntTag;
import org.bukkit.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class RoundManager {
    public int gameRound = 0;

    public int roundTime = 60;
    public int currentRoundTime = roundTime;

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
            gameManager.itController.PlayersTeamList().forEach(
                    player ->
                    gameManager.spawningManager.teleportToGame((Player) player)
            );
        } else if(playerCount <= 7){
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
        currentRoundTime = roundTime - (gameRound * 5);
        runnable();
    }

    public void initRounds(){
        if(Bukkit.getOnlinePlayers().size() <= 1){
            Bukkit.broadcastMessage(ChatColor.RED + "Cannot start game/rounds with only 1 player, report to event organiser (Cryptizism).");
            return;
        }
        if(gameManager.mySQLInit.isConnected()){
            gameManager.hologramScoreboardManager.updateHolo();
        }
        if(gameRound == 0){
            new BukkitRunnable(){
                int counter = 10;
                @Override
                public void run(){
                    for(OfflinePlayer oPlayer : gameManager.itController.PlayersTeamList()){
                        Player player = (Player) oPlayer;
                        player.sendTitle(ChatColor.GOLD + "Starting in " + ChatColor.RED + String.valueOf(counter) + ChatColor.GOLD +  " seconds.", ChatColor.ITALIC + "Hold tight!");
                    }
                    if(counter == 0){
                        gameManager.itController.addAllToPlayers();
                        gameManager.setGameState(GameState.ACTIVE);
                        nextRound();
                        for(OfflinePlayer oPlayer : gameManager.itController.PlayersTeamList()){
                            Player player = (Player) oPlayer;
                            player.sendTitle("", "");
                        }
                        cancel();
                    }
                    counter = counter-1;
                }
            }.runTaskTimer(TntTag.getInstance(), 0, 20);
        } else {
            new BukkitRunnable(){
                @Override
                public void run() {
                    nextRound();
                }
            }.runTaskLater(TntTag.getInstance(), 100);
        }
    }

    public void runnable(){
        new BukkitRunnable(){

            @Override
            public void run(){
                currentRoundTime = currentRoundTime - 1;
                if((currentRoundTime < 0) || gameManager.gameState != GameState.ACTIVE ){
                    if(gameManager.itController.getPlayersSize() > 1) {
                        Bukkit.broadcastMessage(ChatColor.RED + "The round is over");
                    }
                    for(OfflinePlayer oplayer: gameManager.itController.ITTeamList()){
                        Player player = (Player) oplayer;
                        for(Entity e : player.getNearbyEntities(3,2,3)) {
                            if(e instanceof Player) {
                                Player target = (Player)e;
                                explode(target);
                            }
                        }
                        explode(player);
                    }
                    roundEnded();
                    cancel();
                } else {
                    gameManager.scoreboardManager.addToScoreboard(currentRoundTime, gameRound);
                }
            }

        }.runTaskTimer(TntTag.getInstance(), 0, 20);
    }

    public void explode(Player player){
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
        }else if (gameManager.itController.getPlayersSize() == 0){
            //tied
        } else {
            Bukkit.broadcastMessage(gameManager.itController.PlayersTeamList().iterator().next().getName() + " has won!");
            gameManager.setGameState(GameState.LOBBY);
            gameRound = 0;
            currentRoundTime = 0;
            gameManager.scoreboardManager.addToScoreboard(currentRoundTime, gameRound);
        }
    }

    public void setRoundTime(int roundTime) {
        this.roundTime = roundTime;
    }
}
