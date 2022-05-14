package me.cryptizism.tnttag.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardManager {
    public static Scoreboard board;

    private GameManager gameManager;

    public ScoreboardManager(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void createScoreboard(){
        org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();

        System.out.println("Created board");
        Team IT = board.registerNewTeam("IT");
        System.out.println("Created IT");
        Team Players = board.registerNewTeam("Players");
        System.out.println("Created Players");
        Team Spectators = board.registerNewTeam("Spectators");
        System.out.println("Created Spectators");
        IT.setPrefix(ChatColor.DARK_RED + "[IT] ");
        Players.setPrefix(ChatColor.DARK_AQUA + "[PLAYER] ");
        Spectators.setPrefix(ChatColor.GRAY + "[SPECTATOR] ");


    }
    //Get some way to export/reference this board so we can add players to it lols <3
}
