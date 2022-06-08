package me.cryptizism.tnttag.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.HashMap;

public class ScoreboardManager {
    public static Scoreboard board;

    private GameManager gameManager;
    private Objective obj;
    private ArrayList<String> previousScoreboardEntries = new ArrayList<String>();

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
        //reset scores
        clearLeaderboard();
        //set scores
        addEntry(4, ChatColor.translateAlternateColorCodes('&', "&7Round #" + Integer.toString(round)));
        addEntry(3, ChatColor.GOLD + "Time Left:");
        if(timeLeft == 0){
            addEntry(2, ChatColor.RED + "GAME OVER");
        }else{
            addEntry(2, ChatColor.RED + Integer.toString(timeLeft) + " seconds.");
        }
    }

    private void addEntry(int score, String entry){
        Score _score = obj.getScore(entry);
        _score.setScore(score);
        previousScoreboardEntries.add(entry);
    }

    private void clearLeaderboard(){
        for (String previousScoreboardEntry : previousScoreboardEntries) {
            board.resetScores(previousScoreboardEntry);
        }
        previousScoreboardEntries.clear();
    }
}
