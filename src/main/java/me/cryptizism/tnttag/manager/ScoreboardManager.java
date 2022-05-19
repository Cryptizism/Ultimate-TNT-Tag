package me.cryptizism.tnttag.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;

public class ScoreboardManager {
    public static Scoreboard board;

    private GameManager gameManager;
    private Objective obj;

    public ScoreboardManager(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void createScoreboard(){
        org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();
        //Teams
        Team IT = board.registerNewTeam("IT");
        Team Players = board.registerNewTeam("Players");
        Team Spectators = board.registerNewTeam("Spectators");
        //Team Prefixes
        IT.setPrefix(ChatColor.DARK_RED + "[IT] ");
        Players.setPrefix(ChatColor.WHITE + "");
        Spectators.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7&o"));
        obj = board.registerNewObjective(ChatColor.translateAlternateColorCodes('&', "&l&cT&fN&cT Tag"), "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        addToScoreboard(0, 0);
    }

    public void addToScoreboard(int timeLeft, int round) {
        //Scoreboard
        //reset scores (throws no errors when it does not exist?)
        board.resetScores(ChatColor.RED + "GAME OVER");
        board.resetScores(ChatColor.RED + Integer.toString(timeLeft+1) + " seconds.");
        board.resetScores(ChatColor.translateAlternateColorCodes('&', "&7Round #" + Integer.toString(round-1)));
        //set scores
        Score score = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&7Round #" + Integer.toString(round)));
        score.setScore(4);
        Score score2 = obj.getScore(ChatColor.GOLD + "Time Left:");
        score2.setScore(3);
        Score score3;
        if(timeLeft == 0){
            score3 = obj.getScore(ChatColor.RED + "GAME OVER");
        }else{
            score3 = obj.getScore(ChatColor.RED + Integer.toString(timeLeft) + " seconds.");
        }
        score3.setScore(2);
    }
}
