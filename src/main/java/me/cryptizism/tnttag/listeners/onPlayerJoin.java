package me.cryptizism.tnttag.listeners;

import me.cryptizism.tnttag.manager.GameManager;
import me.cryptizism.tnttag.manager.GameState;
import me.cryptizism.tnttag.manager.ScoreboardManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onPlayerJoin implements Listener {

    private final GameManager gameManager;

    public onPlayerJoin(GameManager gameManager){
        this.gameManager = gameManager;

    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        Player caller = event.getPlayer();
        caller.setScoreboard(ScoreboardManager.board);
        if(gameManager.gameState == GameState.ACTIVE){
            gameManager.itController.addToSpec(caller);
        } else if(gameManager.gameState == GameState.LOBBY){
            gameManager.itController.addToPlayer(caller);
        } else {

        }
    }
}
