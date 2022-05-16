package me.cryptizism.tnttag.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;

public class ScoreboardManager {
    public static Scoreboard board;

    private GameManager gameManager;

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
        //Scoreboard
        Objective obj = board.registerNewObjective(ChatColor.translateAlternateColorCodes('&', "&l&cT&fN&cT Tag"), "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&7Round #0"));
        score.setScore(4);
        Score score2 = obj.getScore(ChatColor.GOLD + "Time Left:");
        score2.setScore(3);
        Score score3 = obj.getScore(ChatColor.RED + "0:00");
        score3.setScore(2);
    }
}
