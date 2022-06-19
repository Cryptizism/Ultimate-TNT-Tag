package me.cryptizism.tnttag.listeners;

import me.cryptizism.tnttag.manager.GameManager;
import me.cryptizism.tnttag.manager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class onPlayerLeave implements Listener {

    private final GameManager gameManager;

    public onPlayerLeave(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        gameManager.itController.addToSpec(event.getPlayer());
        if(gameManager.gameState != GameState.ACTIVE){ return; }
        if (((gameManager.itController.getPlayersSize() + gameManager.itController.getITTeamSize()) - 1) <= 1){
            OfflinePlayer winner;
            if(gameManager.itController.getITTeamSize() == 1) {
                winner = gameManager.itController.ITTeamList().iterator().next();
            } else {
                winner = gameManager.itController.PlayersTeamList().iterator().next();
            }
            gameManager.itController.addToPlayer((Player) winner);
            gameManager.roundManager.roundEnded();
        }
    }
}
