package me.cryptizism.tnttag.listeners;

import me.cryptizism.tnttag.manager.GameManager;
import me.cryptizism.tnttag.manager.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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
        ScoreboardManager.board.getTeam("Players").addPlayer((OfflinePlayer) event.getPlayer());
        event.setJoinMessage("Welcome to the server" + event.getPlayer().getName());
    }
}
